package com.pskwiercz.tacocloud.data;

import com.pskwiercz.tacocloud.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String userName);

}
