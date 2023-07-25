package com.revature.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "event_types")
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_type_id")
    private int id;

    @Column(name = "name")
    private String name;
}