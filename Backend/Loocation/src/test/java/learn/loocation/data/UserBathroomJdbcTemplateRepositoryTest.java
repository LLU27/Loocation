package learn.loocation.data;

import learn.loocation.models.UserBathroom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserBathroomJdbcTemplateRepositoryTest {
    @Autowired
    UserBathroomJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }
    @Test
    void shouldAdd() {
        UserBathroom userBathroom = new UserBathroom();
        userBathroom.setUserId(3);
        userBathroom.setBathroomId(2);
        assertTrue(repository.add(userBathroom));
    }

    @Test
    void shouldFindByUserId() {
        assertEquals(2, repository.findByUserId(1).size());
        assertEquals(1, repository.findByUserId(2).size());
    }

    @Test
    void shouldFindByBathroomId() {
        assertEquals(2, repository.findByBathroomId(1).size());
        assertEquals(1, repository.findByBathroomId(3).size());
    }
}