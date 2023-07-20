package com.revature.scheduler.daos;

import com.revature.scheduler.models.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeDAO extends JpaRepository<EventType, Integer> {
}
