package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.model.Role;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Log4j2
@Controller
@RequestMapping(value = "/register-user")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void registerNewUser(@RequestBody UserDTO userDTO) {
        log.info("{}", userDTO);
        userService.saveUser(new User(userDTO.getFirstName(),
                        userDTO.getLastName(),
                        userDTO.getEmail(),
                        userDTO.getPassword(),
                        Collections.singleton(Role.USER)));
    }

    @ExceptionHandler(LoginNotUniqueException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.warn(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + e.getMessage() + "\"}");
    }
}
