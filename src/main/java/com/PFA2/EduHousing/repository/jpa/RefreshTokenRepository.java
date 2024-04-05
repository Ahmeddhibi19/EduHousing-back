package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    public Optional<RefreshToken> findRefreshTokenByToken(String token);
    public Optional<RefreshToken> findRefreshTokenByUserId(Integer userId);
}
