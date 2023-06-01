package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.SecurityUser;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("SecurityUserService")
public class SecurityUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAUX userAUX = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User nor found"));
        return SecurityUser.getUserFromUserAUX(userAUX);
    }
}
