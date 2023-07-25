package com.revature.scheduler;

import com.revature.scheduler.daos.*;
import com.revature.scheduler.dtos.EventDTO;
import com.revature.scheduler.dtos.LocationDTO;
import com.revature.scheduler.dtos.UserDTO;
import com.revature.scheduler.models.*;
import com.revature.scheduler.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    private EventService eventService;

    @Mock
    private UserDAO userDAO;
    @Mock
    private EventDAO eventDAO;
    @Mock
    private StateDAO stateDAO;
    @Mock
    private EventTypeDAO eventTypeDAO;
    @Mock
    private LocationDAO locationDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(userDAO, eventDAO, stateDAO, eventTypeDAO, locationDAO);
    }

    @Test
    public void testGetEventById() {
        Event expectedEvent = new Event();
        expectedEvent.setId(1);

        when(eventDAO.findById(1)).thenReturn(Optional.of(expectedEvent));

        Event actualEvent = eventService.getEventById(1);

        assertEquals(expectedEvent, actualEvent);
        verify(eventDAO).findById(1);
    }

    @Test
    public void testCreateEvent() {
        State state = new State();
        LocationDTO locationDTO = new LocationDTO("Address", "City", "State");
        EventDTO eventDTO = new EventDTO("Event", LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), "Type", locationDTO);
        User user = new User();
        user.setId(1);
        EventType eventType = new EventType();
        eventType.setName("Type");
        Location location = new Location("Address", "City", state);

        when(userDAO.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(eventTypeDAO.findByName(any(String.class))).thenReturn(Optional.of(eventType));
        when(stateDAO.findByName(any(String.class))).thenReturn(Optional.of(state));
        when(locationDAO.save(any(Location.class))).thenReturn(location);
        when(eventDAO.save(any(Event.class))).thenAnswer(i -> i.getArguments()[0]);

        Event actualEvent = eventService.createEvent(1, eventDTO);

        assertEquals(eventDTO.getName(), actualEvent.getName());
        assertEquals(user, actualEvent.getOwner());
        assertEquals(location, actualEvent.getLocation());
        assertEquals(eventType, actualEvent.getType());

        verify(userDAO).findById(any(Integer.class));
        verify(eventTypeDAO).findByName(any(String.class));
        verify(stateDAO).findByName(any(String.class));
        verify(locationDAO).save(any(Location.class));
        verify(eventDAO).save(any(Event.class));
    }

    @Test
    public void testUpdateEventById() {
        State state = new State();
        LocationDTO locationDTO = new LocationDTO("Updated Address", "Updated City", "Updated State");
        EventDTO eventDTO = new EventDTO("Updated Event", LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), "Updated Type", locationDTO);
        EventType updatedEventType = new EventType();
        updatedEventType.setName("Updated Type");
        Location updatedLocation = new Location("Updated Address", "Updated City", state);
        Location oldLocation = new Location("Old Address", "Old City", state);
        Event eventToUpdate = new Event();
        eventToUpdate.setId(1);
        eventToUpdate.setName("Old Event");
        eventToUpdate.setType(new EventType());
        eventToUpdate.setLocation(oldLocation);

        when(eventDAO.getReferenceById(1)).thenReturn(eventToUpdate);
        when(eventTypeDAO.findByName("Updated Type")).thenReturn(Optional.of(updatedEventType));
        when(stateDAO.findByName("Updated State")).thenReturn(Optional.of(state));
        when(locationDAO.save(updatedLocation)).thenReturn(updatedLocation);
        when(eventDAO.save(eventToUpdate)).thenAnswer(i -> i.getArguments()[0]);

        Event updatedEvent = eventService.updateEventById(1, eventDTO);

        assertEquals(eventDTO.getName(), updatedEvent.getName());
        assertEquals(updatedEventType, updatedEvent.getType());
        assertEquals(updatedLocation, updatedEvent.getLocation());

        verify(eventDAO).getReferenceById(1);
        verify(eventTypeDAO).findByName("Updated Type");
        verify(stateDAO).findByName("Updated State");
        verify(locationDAO).save(updatedLocation);
        verify(locationDAO).delete(oldLocation);
        verify(eventDAO).save(eventToUpdate);
    }

    @Test
    public void testGetAllByUserId() {
        List<Event> expectedEvents = new ArrayList<>();
        Event event1 = new Event();
        event1.setId(1);
        expectedEvents.add(event1);

        Event event2 = new Event();
        event2.setId(2);
        expectedEvents.add(event2);

        when(eventDAO.findAllByAuthor(1)).thenReturn(Optional.of(expectedEvents));

        List<Event> actualEvents = eventService.getAllByUserId(1);

        assertIterableEquals(expectedEvents, actualEvents);
        verify(eventDAO).findAllByAuthor(1);
    }




}
