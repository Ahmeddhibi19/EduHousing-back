package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rental_details")
public class RentalDetails extends AbstractEntity{

    @Column(name = "monthly_amount")
    private Double monthlyAmount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_current")
    private Boolean isCurrent ;



    @OneToMany(mappedBy = "rentalDetails", cascade = CascadeType.ALL)
    private Set<Request> requestSet= new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "aprtment_id",nullable = false)
    private Apartment apartment;
}
