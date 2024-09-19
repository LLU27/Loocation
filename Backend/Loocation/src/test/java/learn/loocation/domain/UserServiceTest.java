package learn.loocation.domain;

import learn.loocation.data.UserRepository;
import learn.loocation.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
@Autowired
UserService service;
@MockBean
UserRepository repository;
    @Test
    void shouldFindUserById() {
        User expected = new User(1, "testUser", "password", "test");
        when(repository.findUserById(1)).thenReturn(expected);
        User actual = service.findUserById(1);
        assertEquals(expected, actual);
    }


    @Test
    void shouldFindAllUsers() {
        User user1 = new User(1, "testUser", "password", "test");
        User user2 = new User(2, "testUser", "password", "test");
        when(repository.findAllUsers()).thenReturn(List.of(user1, user2));
        List<User> actual = service.findAllUsers();
        assertEquals(2, actual.size());
        assertNotNull(actual);

    }

    @Test
    void shouldNotFindUserById() {
        when(repository.findUserById(0)).thenReturn(null);
        User actual = service.findUserById(0);
        assertNull(actual);
    }


    @Test
    void shouldAddUser() {
        User expected = new User(1, "testUser", "password", "test");
        User actual = new User(0, "testUser", "password", "test");
        when(repository.addUser(actual)).thenReturn(expected);
        User result = service.addUser(actual).getPayload();
        assertEquals(expected, result);
        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    void shouldNotAddNull() {
        User user = new User();
        Result<User> result = service.addUser(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        User user = new User(0, "", "", "");
        Result<User> result = service.addUser(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWhenInvalidPassword() {
        User user = new User(0, "testUser@gmail.com", "test", "");
        Result<User> result = service.addUser(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("password is required", result.getMessages().get(0));

    }

    @Test
    void shouldNotAddWhenInvalidUsername() {
        User user = new User(0, "testUser@gmail.com", "", "test");
        Result<User> result = service.addUser(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("username is required", result.getMessages().get(0));

    }

    @Test
    void shouldNotAddWhenInvalidEmail() {
        User user = new User(0, "", "password", "test");
        Result<User> result = service.addUser(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("email is required", result.getMessages().get(0));
    }

}