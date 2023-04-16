package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.domain.TourRating;
import com.epam.engx.explorecalifornia.dto.RatingDto;
import com.epam.engx.explorecalifornia.service.TourRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.NoSuchElementException;

@Tag(name = "Tour Rating", description = "The Rating for a Tour")
@Slf4j
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@RequiredArgsConstructor
public class TourRatingController {
    private final TourRatingService tourRatingService;

    @Operation(summary = "Create a tour rating")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Validated RatingDto ratingDto) {
        log.info("POST /tours/{}/ratings", tourId);
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    @Operation(summary = "Lookup a Ratings for a tour")
    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId,
                                                Pageable pageable,
                                                PagedResourcesAssembler<RatingDto> pagedAssembler) {
        log.info("GET /tours/{}/ratings", tourId);
        var tourRatingPage = tourRatingService.lookupRatings(tourId, pageable);
        var ratingDtoList = tourRatingPage.getContent().stream().map(this::toDto).toList();
        return new PageImpl<>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
    }

    @Operation(summary = "Create Several Tour Ratings for one tour, score and several customers")
    @PostMapping("/{score}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam("customers") Integer[] customers) {
        log.info("POST /tours/{}/ratings/{}", tourId, score);
        tourRatingService.rateMany(tourId, score, customers);
    }

    @Operation(summary = "Calculate the average score of the tour")
    @GetMapping("/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        return new AbstractMap.SimpleEntry<>("average", tourRatingService.getAverageScore(tourId));
    }

    @Operation(summary = "Update score and comment of a Tour Rating")
    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId,
                                   @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
            ratingDto.getScore(), ratingDto.getComment()));
    }

    @Operation(summary = "Update score or comment of a Tour Rating")
    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(),
            ratingDto.getScore(), ratingDto.getComment()));
    }

    @Operation(summary = "Delete a Rating of a tour made by a customer")
    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        tourRatingService.delete(tourId, customerId);
    }

    /**
     * Convert the TourRating entity to a RatingDto
     *
     * @param tourRating
     * @return RatingDto
     */
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getCustomerId());
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
