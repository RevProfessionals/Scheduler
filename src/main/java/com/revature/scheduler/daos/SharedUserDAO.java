package com.revature.scheduler.daos;

import com.revature.scheduler.models.SharedUser;
import com.revature.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SharedUserDAO extends JpaRepository<SharedUser,Integer> {

    Optional<SharedUser> findByOwnerAndShared(int ownerId, int sharedId);
    @Query("SELECT DISTINCT su.owner FROM SharedUser su WHERE su.shared.id = :sharedId")
    List<User> findOwnerBySharedId(int sharedId);


}

