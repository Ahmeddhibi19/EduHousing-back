package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "distance")
public class Distance extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @Column(name = "distanceValue")
    private BigDecimal distanceValue;
}
