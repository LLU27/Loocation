package learn.loocation.data;
import learn.loocation.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllUsers() {
        assertEquals(3, repository.findAllUsers().size());
    }

    @Test
    void shouldFindUserById() {
        assertNotEquals(1, repository.findUserById(2).getUserId());
        assertEquals(1, repository.findUserById(1).getUserId());
    }

    @Test
    void shouldAddUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        User result = repository.addUser(user);
        assertNotNull(result);
        assertEquals(4, result.getUserId());
    }

}