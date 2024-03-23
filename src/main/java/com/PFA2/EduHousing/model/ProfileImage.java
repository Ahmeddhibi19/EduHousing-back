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
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
