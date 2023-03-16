package com.Tesis.auth.service;

import com.Tesis.auth.advise.AccessDeniedException;
import com.Tesis.auth.advise.BadRequestException;
import com.Tesis.auth.advise.ErrorCodes;
import com.Tesis.auth.dto.RefreshTokenDto;
import com.Tesis.auth.entity.ERole;
import com.Tesis.auth.entity.RefreshToken;
import com.Tesis.auth.entity.Role;
import com.Tesis.auth.entity.User;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import com.Tesis.auth.payload.response.JwtResponse;
import com.Tesis.auth.payload.response.MessageResponse;
import com.Tesis.auth.payload.response.TokenRefreshResponse;
import com.Tesis.auth.repository.IAuthUserRepository;
import com.Tesis.auth.repository.IRoleRepository;
import com.Tesis.auth.security.Jwt.JwtUtils;
import com.Tesis.auth.security.Jwt.exception.TokenRefreshException;
import com.Tesis.auth.security.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthUserService implements IAuthUserService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IAuthUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IRefreshTokenService refreshTokenService;




    @Override
    public RefreshTokenDto validate(String token) {
        try{
            return new RefreshTokenDto(token);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        if(loginRequest.getEmail()==null){
            throw new BadRequestException("Email not provider", ErrorCodes.EMAIL_NOT_PROVIDER.getCode());
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getEmail(), roles);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return new MessageResponse("Error: Username is already taken!");
//        }

        try{
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
             new AccessDeniedException(ErrorCodes.ACCESS_DENIED.getCode(), "Email not exists");
            }
        }catch (Exception e){
            //throw new AccessDeniedException(ErrorCodes.ACCESS_DENIED.getCode(), "Email not exists");
        }

        // Create new user's account
        User user = new User(signUpRequest);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

//        return new MessageResponse("User registered successfully!");
        return null;
    }

    @Override
    public void logoutUser(RefreshTokenDto refreshTokenDto) {
        try{
//            Optional<RefreshToken> rToken = refreshTokenService.findByToken(refreshTokenDto.getToken());
            refreshTokenService.deleteByToken(refreshTokenDto.getToken());
        }catch (Exception e){

        }

    }

    @Override
    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(ErrorCodes.NOT_FOUND.getCode(),
                        "Refresh token is not in database!"));
    }
}
