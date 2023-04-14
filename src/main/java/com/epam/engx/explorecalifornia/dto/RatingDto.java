package com.epam.engx.explorecalifornia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public final class RatingDto extends RepresentationModel<RatingDto> {
    private final @Min(0) @Max(5) Integer score;
    private final @Size(max = 255) String comment;
    private final @NotNull Integer customerId;
}
