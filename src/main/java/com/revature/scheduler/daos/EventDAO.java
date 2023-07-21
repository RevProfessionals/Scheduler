package com.revature.scheduler.daos;

import com.revature.scheduler.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event, Integer> {
}
