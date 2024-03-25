package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("HOMEOWNER")
public class Homeowner extends User{

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "homeowner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartmentList = new ArrayList<>();


}
