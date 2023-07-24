package com.revature.scheduler.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "shared_user")
public class SharedUser {

    @Id
    @Column(name = "shared_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;


    @ManyToOne
    @JoinColumn(name = "shared_id")
    @JsonBackReference
    private User shared;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public SharedUser(User owner, User shared, Role role) {
        this.owner = owner;
        this.shared = shared;
        this.role = role;
    }
}