package learn.loocation.domain;

import learn.loocation.data.UserBathroomRepository;
import learn.loocation.models.User;
import learn.loocation.models.UserBathroom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserBathroomServiceTest {
    @Autowired
    UserBathroomService service;
    @MockBean
    UserBathroomRepository repository;

    @Test
    void addUserBathroom() {
        UserBathroom expected = new UserBathroom(1, 1);
        UserBathroom actual = new UserBathroom(1, 1);
        Result<UserBathroom> expectedResult = new Result<>();
        expectedResult.setPayload(expected);
        when(repository.add(actual)).thenReturn(expectedResult.isSuccess());
        Result<UserBathroom> actualResult = service.addUserBathroom(actual);
        assertEquals(expected, actualResult.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        UserBathroom userBathroom = new UserBathroom();
        Result<UserBathroom> result = service.addUserBathroom(userBathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmpty() {
        UserBathroom userBathroom = new UserBathroom(0, 0);
        Result<UserBathroom> result = service.addUserBathroom(userBathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullUserId() {
        UserBathroom userBathroom = new UserBathroom(0, 1);
        Result<UserBathroom> result = service.addUserBathroom(userBathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullBathroomId() {
        UserBathroom userBathroom = new UserBathroom(1, 0);
        Result<UserBathroom> result = service.addUserBathroom(userBathroom);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void findBathroomsByUserId() {
        List<UserBathroom> expected = List.of(
                new UserBathroom(1, 1),
                new UserBathroom(2, 1)
        );
        when(repository.findByUserId(1)).thenReturn(expected);
        List<UserBathroom> actual = service.findBathroomsByUserId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindBathroomsByUserId() {
        List<UserBathroom> expected = List.of();
        when(repository.findByUserId(0)).thenReturn(expected);
        List<UserBathroom> actual = service.findBathroomsByUserId(0);
        assertEquals(expected, actual);
    }

    @Test
    void findUsersByBathroomId() {
        List<UserBathroom> expected =List.of(
                new UserBathroom(1, 1),
                new UserBathroom(1, 2)
        );
        when(repository.findByBathroomId(1)).thenReturn(expected);
        List<UserBathroom> actual = service.findUsersByBathroomId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindUsersByBathroomId() {
        List<UserBathroom> expected = List.of();
        when(repository.findByBathroomId(0)).thenReturn(expected);
        List<UserBathroom> actual = service.findUsersByBathroomId(0);
        assertEquals(expected, actual);
    }
}