package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {}
