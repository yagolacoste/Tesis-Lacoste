package com.tesis.Auth.security.Service;

import com.tesis.Auth.advise.ErrorCodes;
import com.tesis.Auth.advise.NotFoundException;
import com.tesis.Auth.entity.User;
import com.tesis.Auth.repository.IAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private IAuthUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.NOT_FOUND.getCode(),"User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }

}
