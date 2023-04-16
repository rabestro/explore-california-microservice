package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.domain.TourRating;
import com.epam.engx.explorecalifornia.dto.RatingDto;
import com.epam.engx.explorecalifornia.repository.TourRepository;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RatingAssembler extends RepresentationModelAssemblerSupport<TourRating, RatingDto> {
    private final RepositoryEntityLinks entityLinks;

    public RatingAssembler(RepositoryEntityLinks entityLinks) {
        super(RatingController.class, RatingDto.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public RatingDto toModel(TourRating entity) {
        var rating = new RatingDto(entity.getScore(), entity.getComment(), entity.getCustomerId());

        // "self" : ".../ratings/{ratingId}"
        var ratingLink = linkTo(methodOn(RatingController.class).getRating(entity.getId()));
        rating.add(ratingLink.withSelfRel());

        //"tour" : ".../tours/{tourId}"
        var tourLink = entityLinks.linkToItemResource(TourRepository.class, entity.getTour().getId());
        rating.add(tourLink.withRel("tour"));
        return rating;
    }
}
