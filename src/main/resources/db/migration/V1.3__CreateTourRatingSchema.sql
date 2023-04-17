CREATE TABLE tour_rating
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    tour_id     BIGINT,
    customer_id BIGINT,
    score       INT,
    comment     VARCHAR(100),
    CONSTRAINT MyConstraint UNIQUE (tour_id, customer_id)
);

ALTER TABLE tour_rating
    ADD CONSTRAINT FK_tour_id FOREIGN KEY (tour_id) REFERENCES tour (id);
