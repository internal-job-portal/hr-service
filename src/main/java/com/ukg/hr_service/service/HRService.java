package com.ukg.hr_service.service;

import com.ukg.hr_service.model.HR;
import com.ukg.hr_service.repository.HRRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class HRService implements UserDetailsService {

    private final HRRepository hrRepository;

    public HRService(HRRepository hrRepository, PasswordEncoder passwordEncoder) {
        this.hrRepository = hrRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HR hr = hrRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("HR not found with email: " + email));
        return new User(hr.getEmail(), hr.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("HR")));
    }

}