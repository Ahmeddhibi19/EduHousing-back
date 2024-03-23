package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "apartment_image")
public class ApartmentImage extends AbstractEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

}
