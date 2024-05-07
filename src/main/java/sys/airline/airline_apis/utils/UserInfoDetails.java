package sys.airline.airline_apis.utils;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import sys.airline.airline_apis.models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserInfoDetails implements UserDetails {
    private Role role;
    String username=null;
    String password=null;
    List<GrantedAuthority> authorities;
    public UserInfoDetails(User user) {
        username = user.getEmail();
        password = user.getPassword();
        authorities = Collections.singletonList(new SimpleGrantedAuthority
                (user.getRole().name()));

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null) {
            return this.role.getAuthorities();
        }
        return Collections.emptyList(); // Return an empty list if role is null
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}

