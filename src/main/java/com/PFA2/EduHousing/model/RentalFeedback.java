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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((apartment == null || apartment.getId() == null) ? 0 : apartment.getId().hashCode());
        result = prime * result + ((student == null || student.getId() == null) ? 0 : student.getId().hashCode());
        return result;
    }
}
