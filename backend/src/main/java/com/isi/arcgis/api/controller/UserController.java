package com.isi.arcgis.api.controller;


import com.isi.arcgis.api.exceptions.ResponseMessage;
import com.isi.arcgis.api.model.User;
import com.isi.arcgis.api.service.SecurityService;
import com.isi.arcgis.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/profile")
    public ResponseEntity<User> isUserAuthenticated() {
        Optional<User> user = userService.findByUsername(securityService.findLoggedInUsername());
        if(user.isEmpty()) {
            throw new AccessDeniedException("User is not logged in");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@RequestBody User user) {
        Optional<User> userDb = userService.findByUsername(securityService.findLoggedInUsername());
        if(userDb.isEmpty()) {
            throw new AccessDeniedException("User is not logged in");
        }

        userDb.get().setAge(user.getAge());
        userDb.get().setUserInterests(user.getUserInterests());

        userService.updateUser(userDb.get());
        return ResponseEntity.status(HttpStatus.OK).body(userDb.get());
    }
}
