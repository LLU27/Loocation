package learn.loocation.data;

import learn.loocation.models.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RatingJdbcTemplateRepositoryTest {

    @Autowired
    RatingJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByRatingId() {
        assertEquals(1, repository.findByRatingId(1).getRatingId());
    }

    @Test
    void shouldAddRating() {
        Rating rating = new Rating();
        rating.setRating(5);
        rating.setUserId(1);
        rating.setBathroomId(1);
        rating.setComment("Great place to visit");
        Rating result = repository.addRating(rating);
        assertNotNull(result);
        assertEquals(6, result.getRatingId());
    }

    @Test
    void shouldUpdateRating() {
      Rating existing = repository.findByRatingId(1);
        existing.setRating(1);
        existing.setComment("Great place to visit");
        assertTrue(repository.updateRating(existing));
        assertEquals(1, repository.findByRatingId(1).getRating());
    }

    @Test
    void shouldNotUpdateRating(){
        Rating rating = new Rating();
        rating.setRatingId(100);
        rating.setRating(1);
        rating.setComment("Great place to visit");
        assertFalse(repository.updateRating(rating));
    }
}