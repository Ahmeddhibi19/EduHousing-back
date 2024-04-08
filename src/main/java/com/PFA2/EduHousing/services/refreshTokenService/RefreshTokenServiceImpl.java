package com.PFA2.EduHousing.services.refreshTokenService;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.RefreshToken;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.repository.jpa.RefreshTokenRepository;
import com.PFA2.EduHousing.repository.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceImpl (
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository
    ){
        this.refreshTokenRepository=refreshTokenRepository;
        this.userRepository=userRepository;
    }
    public RefreshToken createRefreshToken(String email){
        User user=userRepository.findUserByEmailIgnoreCase(email).orElseThrow(
                ()->new EntityNotFoundException("no user with this email :"+email,
                        ErrorCodes.USER_NOT_FOUND)
        );
        Optional<RefreshToken> existingRefreshToken=refreshTokenRepository.findRefreshTokenByUserId(user.getId());
        if (!existingRefreshToken.isEmpty()){
            return existingRefreshToken.get();
        }
        RefreshToken refreshToken=RefreshToken.builder()
                .user(
                        user!=null?
                                user:null
                )
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000 * 60 * 60 * 24*14))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findRefreshTokenByToken(token);
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken()+"Refresh token expired. Please make a new sign in request");
        }
        return refreshToken;
    }
}
