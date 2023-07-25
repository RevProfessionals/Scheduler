package com.revature.scheduler.daos;

import com.revature.scheduler.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface EventDAO extends JpaRepository<Event, Integer> {

    @Query(value ="SELECT * FROM events WHERE owner_id = :userId", nativeQuery = true)
    Optional<List<Event>> findAllByAuthor(@Param("userId") int userId);

//    @Query("SELECT e.id, e.name, e.startDate, e.endDate, e.startTime, e.endTime, e.type, e.location FROM Event e WHERE e.owner.id = :userId")
//    Optional<List<Object[]>> findAllByAuthors(@Param("userId") int userId);

    @Query(value = "select * from events WHERE owner_id = :userId", nativeQuery = true)
    Optional<List<Event>> findAllByAuthors(@Param("userId") int userId);
}
