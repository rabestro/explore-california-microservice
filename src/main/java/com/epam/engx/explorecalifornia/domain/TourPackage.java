package com.epam.engx.explorecalifornia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TourPackage {
    @Id
    private String code;

    @Column
    private String name;
}
