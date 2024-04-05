package com.PFA2.EduHousing.model;

import com.PFA2.EduHousing.model.chat.ChatRoom;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User extends AbstractEntity implements UserDetails {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileImage profileImage;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ApplicationFeedback applicationFeedback;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConfirmationToken confirmationToken;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ConnexionStatus status;

    /*@OneToMany(mappedBy = "sender")
    private List<ChatRoom> sentChatRooms;

    @OneToMany(mappedBy = "recipient")
    private List<ChatRoom> receivedChatRooms;*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
