package com.Tesis.auth.service;

import com.Tesis.auth.entity.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(Long userId);

    public RefreshToken verifyExpiration(RefreshToken token);


    public void deleteByToken(String rToken);
}
