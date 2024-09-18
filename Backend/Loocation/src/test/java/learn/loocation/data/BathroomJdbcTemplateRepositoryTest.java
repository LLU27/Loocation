package learn.loocation.data;

import learn.loocation.models.Address;
import learn.loocation.models.Bathroom;
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
    void shouldFindAllBathrooms() {
        assertEquals(3, repository.findAllBathrooms().size());
    }

    @Test
    void shouldFindBathroomById() {
        assertEquals(1, repository.findBathroomById(1).getBathroomId());
        assertEquals(2, repository.findBathroomById(2).getBathroomId());
        assertNotEquals(1, repository.findBathroomById(2).getBathroomId());
    }

    @Test
    void shouldAddBathroom() {
        Bathroom bathroom = new Bathroom();
        bathroom.setAddress(new Address(1, "123 Test St", "Test City", "TS", "12345", 43,43));
        bathroom.setAccessibility(true);
        bathroom.setUnisex(true);
        bathroom.setChangingStation(true);
        bathroom.setName("Test Bathroom");
        Bathroom result = repository.addBathroom(bathroom);
        assertNotNull(result);
        assertEquals(4, result.getBathroomId());
    }

    @Test
    void shouldUpdateBathroom() {
        Bathroom existing = repository.findBathroomById(1);
        existing.setAccessibility(false);
        existing.setUnisex(false);

        assertTrue(repository.updateBathroom(existing));
        assertEquals(false, repository.findBathroomById(1).hasAccessibility());

    }

    @Test
    void shouldDeleteBathroomById() {
        assertTrue(repository.deleteBathroomById(1));
    }
}