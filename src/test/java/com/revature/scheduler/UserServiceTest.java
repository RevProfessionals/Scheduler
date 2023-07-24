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



}
