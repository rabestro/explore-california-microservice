package com.epam.engx.explorecalifornia.repository;

import com.epam.engx.explorecalifornia.domain.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {
}
