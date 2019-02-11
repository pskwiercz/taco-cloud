package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {}
