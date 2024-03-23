package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(0)
    @Max(10)
    private Integer rating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
