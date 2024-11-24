package com.PFA2.EduHousing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

    @OneToOne//(targetEntity = User.class,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(nullable = false,name = "user_id")
    private User user;
    @Column(name = "expiration_time")
    private Instant expirationDate;

    public ConfirmationToken(User user){
        this.user=user;
        this.confirmationToken= UUID.randomUUID().toString();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((confirmationToken == null) ? 0 : confirmationToken.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        return result;
    }

}
