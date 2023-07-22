package com.revature.scheduler.daos;

import com.revature.scheduler.models.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeDAO extends JpaRepository<EventType, Integer> {
    Optional<EventType> findByName(String name);
}
