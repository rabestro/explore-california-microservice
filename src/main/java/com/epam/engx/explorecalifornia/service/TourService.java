package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.domain.Difficulty;
import com.epam.engx.explorecalifornia.domain.Region;
import com.epam.engx.explorecalifornia.domain.Tour;
import com.epam.engx.explorecalifornia.repository.TourPackageRepository;
import com.epam.engx.explorecalifornia.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(
        String title, String description, String blurb, Integer price,
        String duration, String bullets,
        String keywords, String tourPackageName, Difficulty difficulty, Region region
    ) {
        var tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
            new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(0, title, description, blurb, price, duration,
            bullets, keywords, tourPackage, difficulty, region));
    }

    public long total() {
        return tourRepository.count();
    }
}
