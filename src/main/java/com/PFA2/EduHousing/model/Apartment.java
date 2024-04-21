package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "apartment")
public class Apartment extends AbstractEntity{

    @Column(name = "latitude", precision = 20, scale = 15)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 20, scale = 15)
    private BigDecimal longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "type")
    private String type;

    @Column(name = "is_rented")
    private Boolean isRented;

    @ManyToOne
    @JoinColumn(name = "homeowner_id")
    private Homeowner homeowner;

    @OneToMany(mappedBy = "apartment",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distance> distanceList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartmentImage> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favourites> favouritesSet = new HashSet<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalDetails> rentalDetailsList = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentalFeedback> rentalFeedbackSet = new HashSet<>();

}
