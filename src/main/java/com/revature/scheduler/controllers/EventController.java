package com.revature.scheduler.controllers;


import com.revature.scheduler.dtos.EventDTO;
import com.revature.scheduler.models.Event;
import com.revature.scheduler.security.TokenGenerator;
import com.revature.scheduler.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500"})
public class EventController {

    public final EventService eventService;
    public final TokenGenerator tokenGenerator;

    @Autowired
    public EventController(EventService eventService, TokenGenerator tokenGenerator) {
        this.eventService = eventService;
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping("{id}")
    public Event getEventByIdHandler(@PathVariable("id") int eventId){
        return eventService.getEventById(eventId);
    }

    @PostMapping
    public Event createEventHandler(@RequestHeader("Token") String jwt, @RequestBody EventDTO eventDTO){
        int userId = Integer.parseInt(tokenGenerator.getClaims(jwt).getSubject());
        return eventService.createEvent(userId,eventDTO);
    }

    @PutMapping("{id}")
    public Event updateEventByIdHandler(@PathVariable("id") int eventId, @RequestBody EventDTO eventDTO){
        return eventService.updateEventById(eventId,eventDTO);
    }



}
