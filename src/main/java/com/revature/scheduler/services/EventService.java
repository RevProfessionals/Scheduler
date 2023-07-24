package com.revature.scheduler.services;

import com.revature.scheduler.daos.*;
import com.revature.scheduler.dtos.EventDTO;
import com.revature.scheduler.dtos.LocationDTO;
import com.revature.scheduler.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final UserDAO userDAO;
    private final EventDAO eventDAO;
    private final StateDAO stateDAO;
    private final EventTypeDAO eventTypeDAO;

    private final LocationDAO locationDAO;

    @Autowired
    public EventService(UserDAO userDAO, EventDAO eventDAO, StateDAO stateDAO, EventTypeDAO eventTypeDAO, LocationDAO locationDAO) {
        this.userDAO = userDAO;
        this.eventDAO = eventDAO;
        this.stateDAO = stateDAO;
        this.eventTypeDAO = eventTypeDAO;
        this.locationDAO = locationDAO;
    }
    public Event getEventById(int eventId) {
        return eventDAO.findById(eventId).get();
    }

    public List<Event> getAllByUserId(int userId) {
        return eventDAO.findAllByAuthor(userId);
    }


    public Event createEvent(int userId, EventDTO eventDTO){
        Event event= new Event();
        event.setName(eventDTO.getName());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setType(eventTypeDAO.findByName(eventDTO.getType()).orElseThrow());

        User user = userDAO.findById(userId).get();
        event.setOwner(user);

        LocationDTO locationDTO = eventDTO.getLocation();
        State state = stateDAO.findByName(locationDTO.getState()).orElseThrow();
        Location location = new Location(locationDTO.getAddress(), locationDTO.getCity(), state);

        locationDAO.save(location);
        event.setLocation(location);

        return eventDAO.save(event);
    }

    public Event updateEventById(int eventId, EventDTO eventDTO) {
        Event event = eventDAO.getReferenceById(eventId);

        if(eventDTO.getName() != null){
            event.setName(eventDTO.getName());
        }
        if(eventDTO.getStartDate() != null){
            event.setStartDate(eventDTO.getStartDate());
        }if(eventDTO.getEndDate() != null){
            event.setEndDate(eventDTO.getEndDate());
        }
        if(eventDTO.getStartTime()!=null){
            event.setStartTime(eventDTO.getStartTime());
        }
        if(eventDTO.getEndTime()!=null){
            event.setEndTime(eventDTO.getEndTime());
        }
        if(eventDTO.getType()!=null){
            event.setType(eventTypeDAO.findByName(eventDTO.getType()).orElseThrow());
        }
        if(eventDTO.getLocation() !=null){
            LocationDTO locationDTO = eventDTO.getLocation();
            State state = stateDAO.findByName(locationDTO.getState()).orElseThrow();
            Location location = new Location(locationDTO.getAddress(), locationDTO.getCity(), state);
            Location oldLocation = event.getLocation();
            locationDAO.save(location);
            event.setLocation(location);
            locationDAO.delete(oldLocation);
        }

        return eventDAO.save(event);
    }


}
