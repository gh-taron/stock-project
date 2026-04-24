package com.taron.authentications.repositories;


import com.taron.authentications.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name="users", path ="/users")
public interface UsersProxy {

    @PostMapping()
    User createOne(@RequestBody User user);

    @GetMapping("/existsByPhone/{phone}")
    boolean existsByPhoneNumber(@PathVariable String phone);

    @GetMapping("/findByEmail/{email}")
    Optional<User> findByEmail(@PathVariable String email);
}
