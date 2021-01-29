package com.isi.arcgis.api.controller;

import com.isi.arcgis.api.exceptions.ConflictException;
import com.isi.arcgis.api.exceptions.PasswordRegisterException;
import com.isi.arcgis.api.exceptions.ResponseMessage;
import com.isi.arcgis.api.model.User;
import com.isi.arcgis.api.service.SecurityService;
import com.isi.arcgis.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/me/authenticated")
    public ResponseEntity<ResponseMessage> isUserAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("User is not authenticated");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User is authenticated"));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registration(@RequestBody @Valid User user) {
        // we save the password because save method encodes the password
        String decodedPassword = user.getPassword();
        String passwordConfirm = user.getPasswordConfirm();

        if (!decodedPassword.equals(passwordConfirm)) {
            throw new PasswordRegisterException("Password confirm is not correct");
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("Username is already taken");
        }

        userService.save(user);
        // we send the decoded password to autologin method
        securityService.autoLogin(user.getUsername(), decodedPassword);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Registration successful"));
    }

    @GetMapping("/me/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletRequest request, HttpServletResponse response) {

        String username = securityService.findLoggedInUsername();
        securityService.logout(request, response);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Logout successful for " + username));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        securityService.autoLogin(user.getUsername(), user.getPassword());
        Optional<User> userDb = userService.findByUsername(user.getUsername());
        if(userDb.isEmpty()) {
            throw new AccessDeniedException("Username " + user.getUsername() + " was not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDb.get());
    }
}