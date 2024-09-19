package learn.loocation.domain;

import learn.loocation.data.RatingRepository;
import learn.loocation.models.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RatingServiceTest {
    @Autowired
    RatingService service;

    @MockBean
    RatingRepository repository;


    @Test
    void shouldFindRatingById() {
        Rating expected = new Rating(1, 1, 1, 5, "Great place to visit");
        when(repository.findByRatingId(1)).thenReturn(expected);
        Rating actual = service.findRatingById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindRatingById() {
        when(repository.findByRatingId(0)).thenReturn(null);
        Rating actual = service.findRatingById(0);
        assertNull(actual);
    }

    @Test
    void shouldAddRating() {
        Rating expected = new Rating(1, 1, 1, 5, "Great place to visit");
        Rating actual = new Rating(0, 1, 1, 5, "Great place to visit");
        when(repository.addRating(actual)).thenReturn(expected);
        Rating result = service.addRating(actual).getPayload();
        assertEquals(expected, result);
        assertNotNull(result);
        assertEquals(1, result.getRatingId());
    }

    @Test
    void shouldNotAddNull() {
        Rating rating = new Rating();
        Result<Rating> result = service.addRating(rating);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmpty() {
        Rating rating = new Rating(0, 0, 0, 0, "");
        Result<Rating> result = service.addRating(rating);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        Rating rating = new Rating(0, 0, 0, 0, "");
        Result<Rating> result = service.addRating(rating);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWhenInvalidRating() {
        Rating rating = new Rating(0, 0, 0, 6, "Great place to visit");
        Result<Rating> result = service.addRating(rating);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("rating must be between 1 and 5"));
    }

    @Test
    void shouldUpdateRating() {
        Rating original = new Rating(1, 1, 1, 5, "Great place to visit");
        Rating updated = new Rating(1, 1, 1, 1, "Updated comment");
        when(repository.findByRatingId(1)).thenReturn(original);
        when(repository.updateRating(updated)).thenReturn(true);
        Result<Rating> result = service.updateRating(updated);
        assertTrue(result.isSuccess());
        assertEquals("Updated comment", updated.getComment());
        assertEquals(1, updated.getRating());
    }
    @Test
    void shouldNotUpdateWhenInvalid() {
        Rating rating = new Rating();
        Result<Rating> actual = service.updateRating(rating);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalidRating() {
        Rating rating = new Rating(1, 1, 1, 6, "Great place to visit");
        Result<Rating> result = service.updateRating(rating);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("rating must be between 1 and 5"));
    }

    @Test
    void shouldNotUpdateWhenNotFound() {
        Rating rating = new Rating(1, 1, 1, 5, "Great place to visit");
        when(repository.findByRatingId(1)).thenReturn(null);
        Result<Rating> result = service.updateRating(rating);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }
}