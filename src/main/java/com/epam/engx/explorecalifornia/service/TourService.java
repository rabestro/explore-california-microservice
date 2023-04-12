package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.domain.Tour;
import com.epam.engx.explorecalifornia.repository.TourPackageRepository;
import com.epam.engx.explorecalifornia.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    /**
     * Create a new Tour Object and persist it to the Database
     *
     * @param title           Title of the tour
     * @param tourPackageName tour Package of the tour
     * @param details         Extra details about the tour
     * @return Tour
     */
    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {
        var tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
            new RuntimeException("Tour package does not exist: " + tourPackageName));
        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    /**
     * Calculate the number of Tours in the Database.
     *
     * @return the total.
     */
    public long total() {
        return tourRepository.count();
    }
}
