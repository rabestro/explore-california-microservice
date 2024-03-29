package com.epam.engx.explorecalifornia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TourPackage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String code;

    @Column
    private String name;
}
