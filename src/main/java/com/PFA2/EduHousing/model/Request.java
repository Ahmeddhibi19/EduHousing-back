package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "request")
public class Request extends AbstractEntity{

    @Column(name = "content")
    private String content;

    @Column(name = "status", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "acceptance_time",nullable = true)
    @CreatedDate
    private Instant acceptanceTime;

    @Column(name = "validation_time",nullable = true)
    @CreatedDate
    private Instant validationTime;

    @Column(name = "acceptance_delay")
    private Instant acceptanceDelay ;

    @ManyToOne
    @JoinColumn(name = "rental_details_id",nullable = false)
    private RentalDetails rentalDetails;

    @Column(name = "validation_delay",nullable = true)
    private Instant validationDelay ;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;



}
