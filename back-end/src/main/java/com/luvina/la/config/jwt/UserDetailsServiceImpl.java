package com.luvina.la.config.jwt;

import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.repository.EmployeeEntityRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final EmployeeEntityRepository userRepository;
    UserDetailsServiceImpl(EmployeeEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EmployeeEntity> entity = this.userRepository.findByEmployeeLoginId(username);
        Collection<GrantedAuthority> roles;

        if (entity.isPresent()) {

            // fix all user with ROLE_USER
            roles = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new AuthUserDetails(entity.get(), roles);
        } else {
            throw new UsernameNotFoundException("Employee not found with username: " + username);
        }
    }
}
