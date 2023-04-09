package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.domain.TourPackage;
import com.epam.engx.explorecalifornia.repository.TourPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;

    /**
     * Create a Tour Package
     *
     * @param code code of the package
     * @param name name of the package
     *
     * @return new or existing tour package
     */
    public TourPackage createTourPackage(String code, String name) {
        return tourPackageRepository.findById(code)
            .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public Iterable<TourPackage> lookup(){
        return tourPackageRepository.findAll();
    }

    public long total() {
        return tourPackageRepository.count();
    }
}
