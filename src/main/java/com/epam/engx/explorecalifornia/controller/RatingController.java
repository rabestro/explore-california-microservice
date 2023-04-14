package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.dto.RatingDto;
import com.epam.engx.explorecalifornia.service.TourRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping(path = "/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final TourRatingService tourRatingService;
    private final RatingAssembler assembler;

    @GetMapping
    public CollectionModel<RatingDto> getAll() {
        log.info("GET /ratings");
        return assembler.toCollectionModel(tourRatingService.lookupAll());
    }

    @GetMapping("/{id}")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        log.info("GET /ratings/{}", id);
        return assembler.toModel(tourRatingService.lookupRatingById(id)
            .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"))
        );
    }
}
