package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Integer> {
    Optional<ConfirmationToken> findConfirmationTokenById(Integer id);

    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String confirmationToken);
}
