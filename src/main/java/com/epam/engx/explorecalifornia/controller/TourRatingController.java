package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.domain.TourRating;
import com.epam.engx.explorecalifornia.repository.TourRatingRepository;
import com.epam.engx.explorecalifornia.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@RequiredArgsConstructor
public class TourRatingController {
    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    /**
     * Create a Tour Rating.
     *
     * @param tourId     tour identifier
     * @param tourRating rating data transfer object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId,
                                 @RequestBody @Validated TourRating tourRating) {
        verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
            tourRating.getScore(), tourRating.getComment()));
    }

    /**
     * Lookup a page of Ratings for a tour.
     *
     * @param tourId   Tour Identifier
     * @param pageable paging details
     * @return Requested page of Tour Ratings
     */
    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream()
            .mapToInt(TourRating::getScore).average()
            .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
    }

    /**
     * Update score and comment of a Tour Rating
     *
     * @param tourId     tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
                                    @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId     tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                      @RequestBody @Validated TourRating tourRating) {
        var rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    /**
     * Delete a Rating of a tour made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
                       @PathVariable(value = "customerId") int customerId) {
        var rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    /**
     * Verify and return the TourRating for a particular tourId and Customer
     *
     * @param tourId     tour identifier
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
            .orElseThrow(() -> new NoSuchElementException(
                "Tour-Rating pair for request %s for customer %d".formatted(tourId, customerId))
            );
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId tour identifier
     * @throws NoSuchElementException if no Tour found.
     */
    private void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not exist " + tourId);
        }
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
