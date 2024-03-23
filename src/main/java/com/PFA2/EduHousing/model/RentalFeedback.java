package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rental_feedback")
public class RentalFeedback extends AbstractEntity{

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    @Max(10)
    @Min(0)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "aprtment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
