package com.revature.scheduler.daos;

import com.revature.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);


  boolean existsByEmail(String email);

    @Query("SELECT DISTINCT su.owner FROM SharedUser su WHERE su.shared.id = :userId")
    List<User> findUsersWithSharedCalendar(int userId);

}
