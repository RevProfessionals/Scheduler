package com.revature.scheduler.daos;

import com.revature.scheduler.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantDAO extends JpaRepository<Participant, Integer> {
}
