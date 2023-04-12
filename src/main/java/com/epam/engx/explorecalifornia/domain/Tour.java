package com.epam.engx.explorecalifornia.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * The Tour contains all attributes of an Explore California Tour.
 * Only id, title, and tourPackage are identified and indexed.
 * The rest of the fields are grouped into a Map.
 */
@Document
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Tour implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    private String title;

    @Indexed
    private String tourPackageCode;

    private String tourPackageName;

    private Map<String, String> details;

    /**
     * Construct a fully initialized Tour.
     *
     * @param title       title of the tour
     * @param tourPackage tour package
     * @param details     details about the tour (key-value pairs)
     */
    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
        this.details = details;
    }
}
