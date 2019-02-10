package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);

}
