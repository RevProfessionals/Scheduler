package com.revature.scheduler;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.revature.scheduler.daos.RoleDAO;
import com.revature.scheduler.daos.SharedUserDAO;
import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.dtos.UserDTO;
import com.revature.scheduler.models.Role;
import com.revature.scheduler.models.SharedUser;
import com.revature.scheduler.models.User;
import com.revature.scheduler.services.UserService;

public class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @Mock
    private RoleDAO roleDAO;

    @Mock
    private SharedUserDAO sharedUserDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User user = new User();

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testDeleteUserByIdUserExistsReturnsTrue() {
        int userId = 1;
        User user = new User();

        when(userDAO.existsById(userId)).thenReturn(true);
        when(userDAO.getReferenceById(userId)).thenReturn(user);

        boolean result = userService.deleteUserById(userId);

        assertTrue(result);

        verify(userDAO, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserByIdIfUserDoesNotExistReturnsFalse() {
        int userId = 1;

        when(userDAO.existsById(userId)).thenReturn(false);

        boolean result = userService.deleteUserById(userId);

        assertFalse(result);

        verify(userDAO, never()).delete(any());
    }


    @Test
    public void testGetUserByEmail() {
        String userEmail = "deshondixon@gmail.com";
        User user = new User();

        when(userDAO.findByEmail(userEmail)).thenReturn(user);

        User result = userService.getUserByEmail(userEmail);

        assertEquals(user, result);
    }

    @Test
    public void testShareWithUser() {
        int ownerId = 1;
        int sharedUserId = 2;
        String roleName = "admin";
        User owner = new User();
        User sharedUser = new User();
        Role role = new Role();
        SharedUser shared = new SharedUser(owner, sharedUser, role);

        when(userDAO.findById(ownerId)).thenReturn(Optional.of(owner));
        when(userDAO.findById(sharedUserId)).thenReturn(Optional.of(sharedUser));
        when(roleDAO.findByName(roleName)).thenReturn(Optional.of(role));
        when(sharedUserDAO.save(shared)).thenReturn(shared);

        SharedUser result = userService.shareWithUser(ownerId, sharedUserId, roleName);

        assertNotNull(result);
        assertEquals(shared, result);
    }

    @Test
    public void testUpdateSharedUser() {
        int ownerId = 1;
        int sharedUserId = 2;
        String roleName = "admin";
        Role role = new Role();
        SharedUser shared = new SharedUser();

        when(roleDAO.findByName(roleName)).thenReturn(Optional.of(role));
        when(sharedUserDAO.findByOwnerAndShared(ownerId, sharedUserId)).thenReturn(Optional.of(shared));
        when(sharedUserDAO.save(shared)).thenReturn(shared);

        SharedUser result = userService.updateSharedUser(ownerId, sharedUserId, roleName);

        assertNotNull(result);
        assertEquals(shared, result);
    }
    


}
