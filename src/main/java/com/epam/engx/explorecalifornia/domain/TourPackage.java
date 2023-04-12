package com.epam.engx.explorecalifornia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Classification of Tours.
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String code;

    private String name;
}
