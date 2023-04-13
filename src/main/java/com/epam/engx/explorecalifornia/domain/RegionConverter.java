package com.epam.engx.explorecalifornia.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Hibernate Converter for the Region Enum to DB Column.
 */
@Converter(autoApply = true)
public class RegionConverter implements AttributeConverter<Region, String> {
    @Override
    public String convertToDatabaseColumn(Region region) {
        return region.getLabel();
    }

    @Override
    public Region convertToEntityAttribute(String dbData) {
        return Region.findByLabel(dbData);
    }
}
