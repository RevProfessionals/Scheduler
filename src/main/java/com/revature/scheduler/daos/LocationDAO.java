package com.revature.scheduler.daos;

import com.revature.scheduler.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDAO extends JpaRepository<Location, Integer> {
}
