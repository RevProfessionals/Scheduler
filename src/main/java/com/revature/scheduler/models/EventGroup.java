package com.revature.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "event_groups")
public class EventGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_group_id")
    private int id;

    @ManyToOne
    private Event event;

    @OneToOne
    private User owner;

    @OneToMany(mappedBy = "user", targetEntity = EventGroupUser.class)
    private List<EventGroupUser> userWithAccess;

}