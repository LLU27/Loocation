package learn.loocation.data;

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
    void findUserById() {
    }

    @Test
    void addUser() {
    }
}