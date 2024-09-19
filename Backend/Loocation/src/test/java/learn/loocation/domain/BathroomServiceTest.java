package learn.loocation.domain;

import learn.loocation.data.BathroomRepository;
import learn.loocation.models.Address;
import learn.loocation.models.Bathroom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BathroomServiceTest {
@Autowired
BathroomService service;
@MockBean
BathroomRepository repository;


    Address address = new Address(1, "123 Test St", "Test City", "TS", "12345", 43, 43);
    Address address2 = new Address(2, "321 Test St", "Test City", "TS", "12345", 43, 43);

    @Test
    void shouldFindAllBathrooms() {
        Bathroom bathroom1 = new Bathroom(1, "test bathroom", true, true, true, address);
        Bathroom bathroom2 = new Bathroom(2, "test bathroom", true, true, true, address2);
        List<Bathroom> expected = List.of(bathroom1, bathroom2);
        when(repository.findAllBathrooms()).thenReturn(expected);
        List<Bathroom> actual = service.findAllBathrooms();
        assertEquals(expected, actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindBathroomById() {
        Bathroom expected = new Bathroom(1,"test bathroom",true, true, true, address);
        when(repository.findBathroomById(1)).thenReturn(expected);
        assertEquals(expected, service.findBathroomById(1));
    }

    @Test
    void shouldAddBathroom() {
        Bathroom expected = new Bathroom(1,"test bathroom",true, true, true, address);
        Bathroom actual = new Bathroom(1,"test bathroom",true, true, true, address);
        Result<Bathroom> expectedResult = new Result<>();
        expectedResult.setPayload(expected);
        when(repository.addBathroom(actual)).thenReturn(expected);
        Result<Bathroom> actualResult = service.addBathroom(actual);
        assertEquals(expected, actualResult.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Bathroom bathroom = new Bathroom();
        Result<Bathroom> result = service.addBathroom(bathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmpty() {
        Bathroom bathroom = new Bathroom(0, "", false, false, false, address);
        Result<Bathroom> result = service.addBathroom(bathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullName() {
        Bathroom bathroom = new Bathroom(0, null, false, false, false, address);
        Result<Bathroom> result = service.addBathroom(bathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }


    @Test
    void shouldUpdateBathroom() {
        Bathroom existing = new Bathroom(1,"test bathroom",true, true, true, address);
        when(repository.updateBathroom(existing)).thenReturn(true);
        assertTrue(repository.updateBathroom(existing));
    }
    @Test
    void shouldNotUpdateWhenInvalid() {
        Bathroom bathroom = new Bathroom();
        Result<Bathroom> actual = service.updateBathroom(bathroom);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalidName() {
        Bathroom bathroom = new Bathroom(1, "", false, false, false, address);
        Result<Bathroom> actual = service.updateBathroom(bathroom);
        assertEquals(ResultType.INVALID, actual.getType());
    }
    @Test
    void shouldNotDeleteWhenInvalid() {
        when(repository.deleteBathroomById(0)).thenReturn(false);
        Result<Boolean> result = service.deleteBathroomById(0);
        assertFalse(result.getPayload());
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldDeleteBathroomById() {
        when(repository.deleteBathroomById(1)).thenReturn(true);
        when(repository.deleteBathroomById(999)).thenReturn(false);

        Result<Boolean> result1 = service.deleteBathroomById(1);
        Result<Boolean> result999 = service.deleteBathroomById(999);

        assertTrue(result1.getPayload());
        assertFalse(result999.getPayload());
    }


}