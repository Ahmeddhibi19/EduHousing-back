package com.PFA2.EduHousing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "confirmation_token_table")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "confirmation_token")
    private String confirmationToken;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;
    @Column(name = "expiration_time")
    private Instant expirationDate;

    public ConfirmationToken(User user){
        this.user=user;
        this.confirmationToken= UUID.randomUUID().toString();
    }

}
