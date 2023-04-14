package com.epam.engx.explorecalifornia.dto;

import com.epam.engx.explorecalifornia.domain.TourRating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RatingDto(@Min(0) @Max(5) Integer score, @Size(max = 255) String comment, @NotNull Integer customerId) {
    public RatingDto(TourRating tourRating) {
        this(tourRating.getScore(), tourRating.getComment(), tourRating.getCustomerId());
    }
}
