package com.pskwiercz.tacocloud.controllers;

import com.pskwiercz.tacocloud.data.IngredientRepository;
import com.pskwiercz.tacocloud.data.TacoRepository;
import com.pskwiercz.tacocloud.data.UserRepository;
import com.pskwiercz.tacocloud.domain.*;
import com.pskwiercz.tacocloud.domain.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private UserRepository userRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository,
                                UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute // It will be always added to Model -
                    // needed when design form have errors and ingredients must be display again
    public void addIngredientsToModel(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping //is the same as @RequestMapping("/", method=RequestMethod.GET)
    public String showDesignForm(Model model, Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("taco", new TacoStringBased());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid TacoStringBased tacoStr, BindingResult bindingResult, Order order) {

        if (bindingResult.hasErrors()) {
            return "design";
        }

        Taco tacoToSave = transformStringBasedTaco2EntityTaco(tacoStr);
        log.trace("Taco to save" + tacoToSave.toString());
        Taco saved = tacoRepository.save(tacoToSave);
        order.addTaco(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    private Taco transformStringBasedTaco2EntityTaco(TacoStringBased tacoStr) {
        List<Ingredient> ingredientList = tacoStr.getIngredients()
                .stream()
                .map(strId -> ingredientRepository.findById(strId).get())
                .collect(Collectors.toList());

        Taco taco = new Taco();
        taco.setName(tacoStr.getName());
        taco.setIngredients(ingredientList);

        log.trace(taco.toString());

        return taco;
    }

}
