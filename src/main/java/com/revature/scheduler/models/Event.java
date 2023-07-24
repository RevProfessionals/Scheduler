package com.revature.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @Column(name = "event_name", nullable = false)
    private String name;

    @Column(name = "event_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "event_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "event_start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "event_end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne
    private EventType type;

    @ManyToOne
    private Location location;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "shared", targetEntity = SharedUser.class)
    private List<SharedUser> participant;

}