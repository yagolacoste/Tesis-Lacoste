package com.tesis.Auth.security.service.RefreshToken;

import com.tesis.Auth.models.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(Long userId);

    public RefreshToken verifyExpiration(RefreshToken token);

    public int deleteByUserId(Long userId);
}
