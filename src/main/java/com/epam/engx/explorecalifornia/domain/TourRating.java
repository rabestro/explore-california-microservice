package com.epam.engx.explorecalifornia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name="customer_id")
    private Integer customerId;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    /**
     * Create a fully initialized TourRating.
     *
     * @param tour          the tour.
     * @param customerId    the customer identifier.
     * @param score      Integer score (1-5)
     * @param comment    Optional comment from the customer
     */
    public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
        this.tour = tour;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }

    /**
     * Create a fully initialized TourRating.
     *
     * @param tour          the tour.
     * @param customerId    the customer identifier.
     * @param score      Integer score (1-5)
     */
    public TourRating(Tour tour, Integer customerId, Integer score) {
        this.tour = tour;
        this.customerId = customerId;
        this.score = score;
        this.comment = toComment(score);
    }

    /**
     * Auto Generate a message for a score.
     *
     * @param score
     * @return
     */
    private String toComment(Integer score) {
        return switch (score) {
            case 1 -> "Terrible";
            case 2 -> "Poor";
            case 3 -> "Fair";
            case 4 -> "Good";
            case 5 -> "Great";
            default -> score.toString();
        };
    }
}
