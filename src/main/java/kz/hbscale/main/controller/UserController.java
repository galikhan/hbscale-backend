package kz.hbscale.main.controller;

import kz.hbscale.main.dto.UserDto;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private Logger log = LogManager.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserDto createUser(@RequestBody UserDto user) {
        log.info("user {}", user);
        return userService.createUser(user);
    }
}
