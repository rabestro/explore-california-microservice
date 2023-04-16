package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.dto.RatingDto;
import com.epam.engx.explorecalifornia.service.TourRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Tag(name = "Rating", description = "The Rating")
@Slf4j
@RestController
@RequestMapping(path = "/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final TourRatingService tourRatingService;
    private final RatingAssembler assembler;

    @GetMapping
    @Operation(summary = "Find all ratings")
    @ApiResponse(responseCode = "200", description = "OK")
    public CollectionModel<RatingDto> getAll() {
        log.info("GET /ratings");
        return assembler.toCollectionModel(tourRatingService.lookupAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find rating by id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Rating not found")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        log.info("GET /ratings/{}", id);
        return assembler.toModel(tourRatingService.lookupRatingById(id)
            .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"))
        );
    }
}
