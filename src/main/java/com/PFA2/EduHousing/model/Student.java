package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @OneToOne(mappedBy = "student")
    private ApplicationFeedback applicationFeedback;

    @OneToMany(mappedBy = "student")
    private Set<Favourites> favouritesSet = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<Request> requestSet = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<RentalFeedback> rentalFeedbackSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;
}
