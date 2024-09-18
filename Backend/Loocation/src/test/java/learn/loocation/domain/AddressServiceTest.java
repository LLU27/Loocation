package learn.loocation.domain;

import learn.loocation.data.AddressRepository;
import learn.loocation.models.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AddressServiceTest {
    @Autowired
    AddressService service;
    @MockBean
    AddressRepository repository;

    @Test
    void shouldFindAddressId1() {
        Address expected = new Address('1', "123 Test St", "Test City", "TS", "12345", 43, 43);
        when(repository.findAddressById(1)).thenReturn(expected);
    }
    @Test
    void shouldNotFindAddressId0() {
        Address expected = new Address('1', "123 Test St", "Test City", "TS", "12345", 43, 43);
        when(repository.findAddressById(0)).thenReturn(null);
    }

    @Test
    void shouldAddAddress() {
        Address expected = new Address(0, "123 Test St", "Test City", "TS", "12345", 43, 43);
        Address actual = new Address(1, "123 Test St", "Test City", "TS", "12345", 43, 43);
        when(repository.addAddress(expected)).thenReturn(actual);
        Result<Address> result = service.addAddress(expected);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(actual, result.getPayload());
    }


    @Test
    void shouldNotAddNull() {
        Address address = new Address();
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmpty() {
        Address address = new Address(0, "", "", "", "", 0, 0);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullStreet() {
        Address address = new Address(0, null, "Test City", "TS", "12345", 43, 43);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullCity() {
        Address address = new Address(0, "123 Test St", null, "TS", "12345", 43, 43);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullState() {
        Address address = new Address(0, "123 Test St", "Test City", null, "12345", 43, 43);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullZipCode() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", null, 43, 43);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullLatitude() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", "12345", 0, 43);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullLongitude() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", "12345", 43, 0);
        Result<Address> result = service.addAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }


    @Test
    void shouldUpdateAddress() {
        Address existing = repository.findAddressById(1);
        when(repository.updateAddress(existing)).thenReturn(true);
    }

    @Test
    void shouldNotUpdateNull() {
        Address address = new Address();
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateEmpty() {
        Address address = new Address(0, "", "", "", "", 0, 0);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullStreet() {
        Address address = new Address(0, null, "Test City", "TS", "12345", 43, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullCity() {
        Address address = new Address(0, "123 Test St", null, "TS", "12345", 43, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullState() {
        Address address = new Address(0, "123 Test St", "Test City", null, "12345", 43, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullZipCode() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", null, 43, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullLatitude() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", "12345", 0, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullLongitude() {
        Address address = new Address(0, "123 Test St", "Test City", "TS", "12345", 43, 0);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateInvalidId() {
        Address address = new Address(999, "123 Test St", "Test City", "TS", "12345", 43, 43);
        Result<Address> result = service.updateAddress(address);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void deleteAddressById() {
        when(repository.deleteAddressById(1)).thenReturn(true);
    }

    @Test
    void shouldNotDeleteInvalidId() {
        when(repository.deleteAddressById(999)).thenReturn(false);
    }
}