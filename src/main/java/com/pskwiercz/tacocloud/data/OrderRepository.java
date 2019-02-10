package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.Order;

public interface OrderRepository {

    Order save(Order order);
}
