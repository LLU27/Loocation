package learn.loocation.data;

import learn.loocation.models.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AddressJdbcTemplateRepositoryTest {

    @Autowired
    AddressJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAddressById() {
        assertEquals(1, repository.findAddressById(1).getAddressId());
        assertNotEquals(1, repository.findAddressById(2).getAddressId());
    }

    @Test
    void shouldAddAddress() {
        Address address = new Address();
        address.setStreet("123 Test St");
        address.setCity("Test City");
        address.setState("TS");
        address.setZipCode("12345");
        address.setLatitude(43);
        address.setLongitude(43);
        Address result = repository.addAddress(address);
        assertNotNull(result);
        assertEquals(4, result.getAddressId());
    }

    @Test
    void shouldUpdateAddress() {
        Address existing = repository.findAddressById(1);
        existing.setStreet("321 Test St");

        assertTrue(repository.updateAddress(existing));
        assertEquals("321 Test St", repository.findAddressById(1).getStreet());

    }

    @Test
    void shouldDeleteAddressById() {
        assertTrue(repository.deleteAddressById(1));
        assertFalse(repository.deleteAddressById(1));
    }
}