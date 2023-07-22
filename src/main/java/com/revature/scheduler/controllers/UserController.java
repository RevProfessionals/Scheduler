package com.revature.scheduler.controllers;

import com.revature.scheduler.dtos.UserDTO;
import com.revature.scheduler.models.User;
import com.revature.scheduler.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{id}")
    public Optional<User> getUserByIdHandler(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @GetMapping("email/{email}")
    public User getUserByEmailHandler(@PathVariable("email") String email){
        return userService.getUserByEmail(email);
    }

    @PutMapping("{id}")
    public User updateUserHandler(@PathVariable("id") int id, @RequestBody UserDTO userDTO){
        return userService.updateUser(id,userDTO);
    }

    @PutMapping("{id}/role/{roleId}")
    public User updateUserRoleHandler(@PathVariable("id")int id, @PathVariable("roleId") int roleId){
        return userService.updateUserRole(id,roleId);
    }

    @DeleteMapping("{id}")
    public boolean deleteUserByIdHandler(@PathVariable("id") int id){
        return userService.deleteUserById(id);
    }



}
