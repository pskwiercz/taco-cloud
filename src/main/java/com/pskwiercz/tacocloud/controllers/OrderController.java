package com.pskwiercz.tacocloud.controllers;

import com.pskwiercz.tacocloud.config.OrderProperties;
import com.pskwiercz.tacocloud.data.OrderRepository;
import com.pskwiercz.tacocloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProperties orderProperties;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderProperties orderProperties) {
        this.orderRepository = orderRepository;
        this.orderProperties = orderProperties;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("msg", orderProperties.getWelcomeMsg());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.trace("Order tosave" + order.toString());
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
