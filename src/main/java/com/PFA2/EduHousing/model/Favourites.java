package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "favourites")
public class Favourites extends AbstractEntity{
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;


}
