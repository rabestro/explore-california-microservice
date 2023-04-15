package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.domain.Tour;
import com.epam.engx.explorecalifornia.domain.TourRating;
import com.epam.engx.explorecalifornia.repository.TourRatingRepository;
import com.epam.engx.explorecalifornia.repository.TourRepository;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.offset;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TourRatingServiceTest {
    private static final int CUSTOMER_ID = 123;
    private static final int TOUR_ID = 1;
    private static final int TOUR_RATING_ID = 100;
    private static final Offset<Double> DELTA = offset(0.0001);
    private static final int AVERAGE_SCORE = 10;

    @Mock
    private TourRepository tourRepositoryMock;
    @Mock
    private TourRatingRepository tourRatingRepositoryMock;
    @InjectMocks
    private TourRatingService service;
    @Mock
    private Tour tourMock;
    @Mock
    private TourRating tourRatingMock;

    @BeforeEach
    void setUp() {
        lenient()
            .when(tourRepositoryMock.findById(TOUR_ID))
            .thenReturn(Optional.of(tourMock));
        lenient()
            .when(tourMock.getId())
            .thenReturn(TOUR_ID);
        lenient()
            .when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID))
            .thenReturn(Optional.of(tourRatingMock));
        lenient()
            .when(tourRatingRepositoryMock.findByTourId(TOUR_ID))
            .thenReturn(List.of(tourRatingMock));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void lookup_rating_by_id() {
        given(tourRatingRepositoryMock.findById(TOUR_RATING_ID))
            .willReturn(Optional.of(tourRatingMock));

        then(service.lookupRatingById(TOUR_RATING_ID))
            .isPresent()
            .contains(tourRatingMock);
    }

    @Test
    void lookup_all_ratings() {
        given(tourRatingRepositoryMock.findAll())
            .willReturn(List.of(tourRatingMock));

        then(service.lookupAll())
            .hasSize(1)
            .containsExactly(tourRatingMock);
    }

    @Test
    void getAverageScore() {
        given(tourRatingMock.getScore())
            .willReturn(AVERAGE_SCORE);

        then(service.getAverageScore(TOUR_ID))
            .isCloseTo(AVERAGE_SCORE, DELTA);
    }

    @Test
    void lookupRatings() {
        var pageable = mock(Pageable.class);
        var page = mock(Page.class);

        given(tourRatingRepositoryMock.findByTourId(TOUR_ID, pageable))
            .willReturn(page);

        then(service.lookupRatings(TOUR_ID, pageable))
            .isEqualTo(page);
    }

    @Test
    void delete() {
        // when
        service.delete(TOUR_ID, CUSTOMER_ID);

        // then
        verify(tourRatingRepositoryMock)
            .delete(any(TourRating.class));
    }


    @Test
    void update() {
        service.update(TOUR_ID, CUSTOMER_ID, 5, "great");

        verify(tourRatingRepositoryMock)
            .save(any(TourRating.class));

        verify(tourRatingMock)
            .setComment("great");
        verify(tourRatingMock)
            .setScore(5);
    }

    @Test
    void update_some() {
        service.updateSome(TOUR_ID, CUSTOMER_ID, 1, "awful");

        verify(tourRatingRepositoryMock)
            .save(any(TourRating.class));

        verify(tourRatingMock)
            .setComment("awful");
        verify(tourRatingMock)
            .setScore(1);
    }

    @Test
    void rate_many() {
        // when
        service.rateMany(TOUR_ID, 10, new Integer[]{CUSTOMER_ID, CUSTOMER_ID + 1});

        // then
        verify(tourRatingRepositoryMock, times(2))
            .save(any(TourRating.class));
    }

    @Test
    void createNew() {
        // given
        var tourRatingCaptor = ArgumentCaptor.forClass(TourRating.class);

        // when
        service.createNew(TOUR_ID, CUSTOMER_ID, 2, "ok");

        // then
        verify(tourRatingRepositoryMock)
            .save(tourRatingCaptor.capture());

        var rating = tourRatingCaptor.getValue();

        then(rating.getTour())
            .isEqualTo(tourMock);

        then(rating.getCustomerId())
            .isEqualTo(CUSTOMER_ID);

        then(rating.getScore())
            .isEqualTo(2);

        then(rating.getComment())
            .isEqualTo("ok");
    }
}
