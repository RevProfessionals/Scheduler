package com.revature.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "event_group_user")
public class EventGroupUser {

    @Id
    @Column(name = "event_group_user_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_group_id")
    private EventGroup eventGroup;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}