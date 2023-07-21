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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "event")
    private List<EventUser> eventList;

    @OneToMany(mappedBy = "eventGroup")
    private List<EventGroupUser> eventGroupAccessor;

}