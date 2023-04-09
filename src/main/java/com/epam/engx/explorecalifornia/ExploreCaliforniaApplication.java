package com.epam.engx.explorecalifornia;

import com.epam.engx.explorecalifornia.domain.Difficulty;
import com.epam.engx.explorecalifornia.domain.Region;
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
    private void createTourPackages(){
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
        TourFromFile.read(fileToImport).forEach(importedTour ->
            tourService.createTour(importedTour.getTitle(),
                importedTour.getDescription(),
                importedTour.getBlurb(),
                importedTour.getPrice(),
                importedTour.getLength(),
                importedTour.getBullets(),
                importedTour.getKeywords(),
                importedTour.getPackageType(),
                importedTour.getDifficulty(),
                importedTour.getRegion()));
    }

    /**
     * Helper class to import ExploreCalifornia.json
     */
    private static class TourFromFile {

        //fields
        private String packageType, title, description, blurb, price, length,
            bullets, keywords, difficulty, region;

        //reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
        }
        protected TourFromFile(){}

        String getPackageType() { return packageType; }

        String getTitle() { return title; }

        String getDescription() { return description; }

        String getBlurb() { return blurb; }

        Integer getPrice() { return Integer.parseInt(price); }

        String getLength() { return length; }

        String getBullets() { return bullets; }

        String getKeywords() { return keywords; }

        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }

        Region getRegion() { return Region.findByLabel(region); }
    }
}
