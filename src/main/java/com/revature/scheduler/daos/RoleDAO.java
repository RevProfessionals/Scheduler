package com.revature.scheduler.daos;

import com.revature.scheduler.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(String name);
}
