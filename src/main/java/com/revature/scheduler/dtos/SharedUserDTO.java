package com.revature.scheduler.dtos;

import com.revature.scheduler.models.Event;
import com.revature.scheduler.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SharedUserDTO {
    private User user;
    private List<Event> events;
}
