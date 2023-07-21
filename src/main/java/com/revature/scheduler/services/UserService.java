package com.revature.scheduler.services;

import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.models.Role;
import com.revature.scheduler.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;


    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> getUserById(int id){
        return userDAO.findById(id);
    }

    public User getUserByEmail(String email){
        return userDAO.findByEmail(email);
    }

    public User updateUser(int id, User user){
        User u = userDAO.getReferenceById(id);

        if(user.getEmail() != null){
            u.setEmail(user.getEmail());
        }
        if(user.getFirstName() != null){
            u.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            u.setLastName(user.getLastName());
        }
        if(user.getPassword() != null){
            u.setPassword(user.getPassword());
        }
        return userDAO.save(u);
    }

    public User updateUserRole(int id, int roleId){
        User u = userDAO.getReferenceById(id);
        Role r = new Role();

        r.setRoleId(roleId);
        u.setRole(r);

        return userDAO.save(u);
    }

    public boolean deleteUserById(int id){
        if(userDAO.existsById(id)){
            User u = userDAO.getReferenceById(id);
            userDAO.delete(u);
            return true;
        }
        return false;
    }



}
