package com.tesis.Auth.config;

import com.tesis.Auth.Dto.UserDto;
import com.tesis.Auth.Dto.UserTokenDto;
import com.tesis.Auth.models.User;
import com.tesis.Auth.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user=userRepository.findByUsername(username).map(u->new UserDto(u))
                .orElseThrow(()-> new UsernameNotFoundException("User not Found with username"+username));

        return  UserDetailsImpl.build(user);
    }
}
