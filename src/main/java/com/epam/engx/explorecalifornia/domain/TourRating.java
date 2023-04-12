package com.epam.engx.explorecalifornia.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Rating of a Tour by a Customer
 */
@Document
@Data
@NoArgsConstructor
public class TourRating {
    @Id
    private String id;

    private String tourId;

    @NotNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    /**
     * Construct a new Tour Rating.
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @param score Integer score (1-5)
     * @param comment Optional comment from the customer
     */
    public TourRating(String tourId, Integer customerId, Integer score, String comment) {
        this.tourId = tourId;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }
}
