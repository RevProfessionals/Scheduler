package com.revature.scheduler.daos;

import com.revature.scheduler.models.SharedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedUserDAO extends JpaRepository<SharedUser,Integer> {

    Optional<SharedUser> findByOwnerAndShared(int ownerId, int sharedId);

}
