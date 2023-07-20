package com.revature.scheduler.daos;

import com.revature.scheduler.models.EventGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGroupDAO extends JpaRepository<EventGroup, Integer> {
}
