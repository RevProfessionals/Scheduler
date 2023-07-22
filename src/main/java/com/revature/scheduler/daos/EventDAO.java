package com.revature.scheduler.daos;

import com.revature.scheduler.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventDAO extends JpaRepository<Event, Integer> {

    @Query(value ="SELECT * FROM events WHERE author_user_id = :userId", nativeQuery = true)
    List<Event> findAllByAuthor(@Param("userId") int userId);



}
