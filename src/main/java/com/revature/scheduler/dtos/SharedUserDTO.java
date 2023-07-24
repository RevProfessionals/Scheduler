package com.revature.scheduler.dtos;

import com.revature.scheduler.models.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data

public class SharedUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<Event> events;
}
