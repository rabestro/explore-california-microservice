package com.epam.engx.explorecalifornia;

import com.epam.engx.explorecalifornia.service.TourPackageService;
import com.epam.engx.explorecalifornia.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
@RequiredArgsConstructor
public class ExploreCaliforniaApplication implements CommandLineRunner {
    private final TourPackageService tourPackageService;
    private final TourService tourService;

    @Value("${ec.importfile}")
    private String importFile;

    public static void main(String[] args) {
        SpringApplication.run(ExploreCaliforniaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        createTours(importFile);
    }

    /**
     * Initialize all the known tour packages
     */
    private void createTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }

    /**
     * Create tour entities from an external file
     */
    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(tourFromFile ->
            tourService.createTour(tourFromFile.getTitle(),
                tourFromFile.getPackageName(), tourFromFile.getDetails())
        );
    }

    /**
     * Helper class to import ExploreCalifornia.json
     */
    private static class TourFromFile {
        String title;
        String packageName;
        Map<String, String> details;

        TourFromFile(Map<String, String> tour) {
            this.title = tour.get("title");
            this.packageName = tour.get("packageType");
            this.details = tour;
            this.details.remove("packageType");
            this.details.remove("title");
        }

        //reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            var records = new ObjectMapper().setVisibility(FIELD, ANY).
                readValue(new FileInputStream(fileToImport),
                    new TypeReference<List<Map<String, String>>>() {
                    });
            return records.stream().map(TourFromFile::new).toList();
        }

        String getTitle() {
            return title;
        }

        String getPackageName() {
            return packageName;
        }

        Map<String, String> getDetails() {
            return details;
        }
    }
}
