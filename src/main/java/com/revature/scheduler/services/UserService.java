package com.revature.scheduler.services;

import com.revature.scheduler.daos.EventDAO;
import com.revature.scheduler.daos.RoleDAO;
import com.revature.scheduler.daos.SharedUserDAO;
import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.dtos.SharedUserDTO;
import com.revature.scheduler.dtos.UserDTO;
import com.revature.scheduler.models.Event;
import com.revature.scheduler.models.Role;
import com.revature.scheduler.models.SharedUser;
import com.revature.scheduler.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final SharedUserDAO sharedUserDAO;
    private EventService eventService;
    private final EventDAO eventDAO;


    @Autowired
    public UserService(UserDAO userDAO, RoleDAO roleDAO, RoleDAO roleDAO1, SharedUserDAO sharedUserDAO, EventDAO eventDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO1;
        this.sharedUserDAO = sharedUserDAO;
        this.eventDAO = eventDAO;
    }

    public Optional<User> getUserById(int id) {
        return userDAO.findById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User updateUser(int id, UserDTO userDTO) {
        User user = userDAO.findById(id).orElseThrow();

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        return userDAO.save(user);
    }

    public SharedUser shareWithUser(int ownerId, int sharedUserId, String roleName) {
        User owner = userDAO.findById(ownerId).orElseThrow();
        User sharedUser = userDAO.findById(sharedUserId).orElseThrow();
        Role role = roleDAO.findByName(roleName).orElseThrow();

        SharedUser shared = new SharedUser(owner, sharedUser, role);

        return sharedUserDAO.save(shared);
    }

    public SharedUser updateSharedUser(int ownerId, int sharedUserId, String roleName) {
        Role role = roleDAO.findByName(roleName).orElseThrow();
        SharedUser shared = sharedUserDAO.findByOwnerAndShared(ownerId, sharedUserId).orElseThrow();
        shared.setRole(role);
        return sharedUserDAO.save(shared);

    }

    public boolean deleteUserById(int id) {
        if (userDAO.existsById(id)) {
            User user = userDAO.getReferenceById(id);
            userDAO.delete(user);
            return true;
        }
        return false;
    }

    public List<SharedUserDTO> findUsersWithSharedCalendar(int sharedId) {
        List<User> owners = sharedUserDAO.findOwnerBySharedId(sharedId);
        List<SharedUserDTO> calendars = new ArrayList<>();

        for (User owner : owners) {
            List<Event> events = eventDAO.findAllByAuthors(owner.getId()).orElseThrow();
            calendars.add(new SharedUserDTO(owner, events));
        }
        return calendars;

    }
}
