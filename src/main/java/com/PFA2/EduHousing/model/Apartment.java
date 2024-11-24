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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((isRented == null) ? 0 : isRented.hashCode());
        result = prime * result + ((homeowner == null || homeowner.getId() == null) ? 0 : homeowner.getId().hashCode());
        result = prime * result + ((city == null || city.getId() == null) ? 0 : city.getId().hashCode());
        result = prime * result + ((imageList == null) ? 0 : imageList.hashCode());
        result = prime * result + ((rentalDetailsList == null) ? 0 : rentalDetailsList.hashCode());
        result = prime * result + ((distanceList == null) ? 0 : distanceList.hashCode());
        result = prime * result + ((favouritesSet == null) ? 0 : favouritesSet.hashCode());
        return result;
    }


}
