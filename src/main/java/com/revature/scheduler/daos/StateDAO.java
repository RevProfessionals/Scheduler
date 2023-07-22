package com.revature.scheduler.daos;

import com.revature.scheduler.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateDAO extends JpaRepository<State, Integer> {

    Optional<State> findByName(String name);
}
