package com.exercise.accountingNotebook.controller;

import com.exercise.accountingNotebook.model.User;
import com.exercise.accountingNotebook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping(value = "/accountingNotebook/api/user")
@Api(value="User Controller", description="Controller for User data.")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return User by userId", response = User.class)
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return User created", response = User.class)
    public ResponseEntity<?> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("{userId}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return User updated", response = User.class)
    public ResponseEntity<?> update(@PathVariable("userId") Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }
}
