package com.tesis.Auth.security.service.auth;

import com.tesis.Auth.Dto.UserDto;
import com.tesis.Auth.Exception.TokenRefreshException;
import com.tesis.Auth.config.ERole;
import com.tesis.Auth.models.RefreshToken;
import com.tesis.Auth.models.Role;
import com.tesis.Auth.models.User;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.MessageResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;
import com.tesis.Auth.repository.IRoleRepository;
import com.tesis.Auth.repository.IUserRepository;
import com.tesis.Auth.security.jwt.IJwtUtils;
import com.tesis.Auth.config.UserDetailsImpl;
import com.tesis.Auth.security.service.RefreshToken.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    IJwtUtils jwtUtils;

    @Autowired
    IRefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt=jwtUtils.generateJwtToken(userDetails);

        List<String> role=userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken= refreshTokenService.createRefreshToken(userDetails.getUserDto().getId());

        return ResponseEntity.ok( new JwtResponse(jwt,refreshToken.getToken(),userDetails.getUserDto(),role));

    }

    @Override
    public ResponseEntity<?> register(UserDto userDto) {
        try {
            if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
            }
        }catch (Exception e){


        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        String pass= encoder.encode(userDto.getPassword());
        User user = new User(userDto);
        user.setPassword(pass);

        Set<String> strRoles = userDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER.getRole())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.getRole())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR.getRole())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER.getRole())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> logout(JwtResponse jwtResponse) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserDto().getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @Override
    public ResponseEntity<?>  refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken= tokenRefreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user ->{
                    String token=jwtUtils.getUserNameFromJwtToken(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token,requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
