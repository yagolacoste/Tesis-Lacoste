package com.tesis.Auth.service;

import com.tesis.Auth.entity.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(Long userId);

    public RefreshToken verifyExpiration(RefreshToken token);


    public void deleteByToken(String rToken);
}
