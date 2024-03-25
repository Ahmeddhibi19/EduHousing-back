package com.PFA2.EduHousing.services.refreshTokenService;

import com.PFA2.EduHousing.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String email);

    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken verifyExpiration(RefreshToken refreshToken);
}
