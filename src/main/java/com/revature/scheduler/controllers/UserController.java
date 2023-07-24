package com.revature.scheduler.controllers;

import com.revature.scheduler.dtos.UserDTO;
import com.revature.scheduler.models.Event;
import com.revature.scheduler.models.SharedUser;
import com.revature.scheduler.models.User;
import com.revature.scheduler.services.EventService;
import com.revature.scheduler.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500"})
public class UserController {
    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    public Optional<User> getUserByIdHandler(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @GetMapping("emails/{email}")
    public User getUserByEmailHandler(@PathVariable("email") String email){
        return userService.getUserByEmail(email);
    }

    @PutMapping("{id}")
    public User updateUserHandler(@PathVariable("id") int id, @RequestBody UserDTO userDTO){
        return userService.updateUser(id,userDTO);
    }

    //Extend getAll to allow us to see events by a user that shares their events with us
    @GetMapping("{id}/events")
    public List<Event> getAllByUserIdHandler(@PathVariable("id") int userId){
        return eventService.getAllByUserId(userId);
    }

    @DeleteMapping("{id}")
    public boolean deleteUserByIdHandler(@PathVariable("id") int id){
        return userService.deleteUserById(id);
    }

    @PostMapping("{id}/shared/{sharedId}")
    public SharedUser shareWithUserHandler(@PathVariable("id") int ownerId, @PathVariable("sharedId") int sharedId, @RequestBody String roleName){return userService.shareWithUser(ownerId,sharedId,roleName);}

    @PutMapping("{id}/shared/{sharedId}")
    public SharedUser updateSharedUserRole(@PathVariable("id") int ownerId, @PathVariable("sharedId") int sharedId, @RequestBody String roleName){return userService.updateSharedUser(ownerId,sharedId,roleName);}

    @GetMapping("{id}/calendars")
    public List<User> getSharedCalendars(@PathVariable("id") int userId) {
        return userService.findUsersWithSharedCalendar(userId);
    }

}
