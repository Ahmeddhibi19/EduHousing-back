package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

    @Column(name = "address")
    private String address;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favourites> favouritesSet = new HashSet<>();


    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Request> requestSet = new HashSet<>();


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentalFeedback> rentalFeedbackSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @Override
    public int hashCode() {
        return Objects.hash(address, favouritesSet, rentalFeedbackSet, college);
    }
}
