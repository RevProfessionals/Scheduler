package com.revature.scheduler.dtos;

import com.revature.scheduler.models.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data

public class EventDTO {

    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type;
    private LocationDTO location;
}
