package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.Taco;

public interface TacoRepository {

    Taco save(Taco taco);
}
