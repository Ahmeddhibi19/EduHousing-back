package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "city")
public class City extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<College> collegeList = new ArrayList<>();
}
