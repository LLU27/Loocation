package learn.loocation.data;

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
    void add() {
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findByBathroomId() {
    }
}