package learn.loocation.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BathroomJdbcTemplateRepositoryTest {
    @Autowired
    BathroomJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }
    @Test
    void findAllBathrooms() {
    }

    @Test
    void findBathroomById() {
    }

    @Test
    void addBathroom() {
    }

    @Test
    void updateBathroom() {
    }

    @Test
    void deleteBathroomById() {
    }
}