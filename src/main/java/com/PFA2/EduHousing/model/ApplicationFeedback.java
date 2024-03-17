package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "application_feedback")
public class ApplicationFeedback extends AbstractEntity{

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @OneToOne(optional = false)
    @JoinColumn(name = "homeowner_id")
    private Homeowner homeowner;

    @OneToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;


}
