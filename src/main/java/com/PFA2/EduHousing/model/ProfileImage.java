package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "profileimage")
public class ProfileImage extends AbstractEntity{
    @Lob
    @Column(name = "data", columnDefinition = "BLOB")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
