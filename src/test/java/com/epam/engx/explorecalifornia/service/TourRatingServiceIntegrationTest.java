package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.domain.TourRating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TourRatingServiceIntegrationTest {
    private static final int CUSTOMER_ID = 456;
    private static final int TOUR_ID = 1;
    private static final int NOT_A_TOUR_ID = 123;
    private static final int CUSTOMER_ID1 = 1234;

    @Autowired
    private TourRatingService service;

    @Test
    void delete_existing_tour_rating() {
        // given
        var tourRatings = service.lookupAll();
        var firstRating = tourRatings.get(0);

        // when
        service.delete(firstRating.getTour().getId(), firstRating.getCustomerId());

        then(service.lookupAll())
            .hasSize(tourRatings.size() - 1);
    }

    @Test()
    void delete_non_existing_tour_rating() {
        assertThrows(NoSuchElementException.class, () ->
            service.delete(NOT_A_TOUR_ID, CUSTOMER_ID1)
        );
    }

    @Test
    void create_a_new_tour_rating() {
        // given
        service.createNew(TOUR_ID, CUSTOMER_ID, 2, "it was fair");

        // when
        var newTourRating = service.verifyTourRating(TOUR_ID, CUSTOMER_ID);

        then(newTourRating.getTour().getId())
            .isEqualTo(TOUR_ID);
        then(newTourRating.getCustomerId())
            .isEqualTo(CUSTOMER_ID);
        then(newTourRating.getScore())
            .isEqualTo(2);
        then(newTourRating.getComment())
            .isEqualTo("it was fair");
    }
}
