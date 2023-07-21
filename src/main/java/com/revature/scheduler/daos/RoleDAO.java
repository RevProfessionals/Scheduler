package com.revature.scheduler.daos;

import com.revature.scheduler.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
