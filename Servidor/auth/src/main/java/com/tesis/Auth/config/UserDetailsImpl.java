package com.tesis.Auth.config;

import com.tesis.Auth.Dto.UserDto;
import com.tesis.Auth.Dto.UserTokenDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {

    private UserTokenDto userDto;

    private Collection<?extends GrantedAuthority>authorities;

    public UserDetailsImpl(UserTokenDto userDto, Collection<? extends GrantedAuthority> authorities) {
        this.userDto = userDto;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserDto userDto) {
        List<GrantedAuthority> authorities = userDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        UserTokenDto userTokenDto=new UserTokenDto(userDto);
        return new UserDetailsImpl(
                userTokenDto,
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserTokenDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserTokenDto userDto) {
        this.userDto = userDto;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userDto.getId(), user.getUserDto().getId());
    }
}
