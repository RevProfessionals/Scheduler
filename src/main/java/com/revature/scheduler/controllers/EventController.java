package com.revature.scheduler.controllers;


import com.revature.scheduler.models.Event;
import com.revature.scheduler.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("event")
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500"})
public class EventController {

    public final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    public Event getEventByIdHandler(@PathVariable("id") int eventId){
        return eventService.getEventById(eventId);
    }

    @PostMapping("user/{id}")
    public Event createEventHandler(@PathVariable("id") int userId, @RequestBody Event event){
        return eventService.createEvent(userId,event);
    }

    @PutMapping("{id}")
    public Event updateEventByIdHandler(@PathVariable("id") int eventId, @RequestBody Event event){
        return eventService.updateEventById(eventId,event);
    }



}
