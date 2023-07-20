package com.revature.scheduler.daos;

import com.revature.scheduler.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDAO extends JpaRepository<State, Integer> {
}
