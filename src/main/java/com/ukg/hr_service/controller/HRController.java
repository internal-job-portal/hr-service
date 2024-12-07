package com.ukg.hr_service.controller;

import com.ukg.hr_service.dto.LoginRequest;
import com.ukg.hr_service.dto.UserDetailsDTO;
import com.ukg.hr_service.model.HR;
import com.ukg.hr_service.model.Notification;
import com.ukg.hr_service.repository.HRRepository;
import com.ukg.hr_service.service.HRService;
import com.ukg.hr_service.service.NotificationService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr")
public class HRController {
    @Autowired
    private HRService hrService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private HRRepository hrRepository;


    @PostMapping("/verify")
    public ResponseEntity<UserDetailsDTO> verifyCredentials(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails userDetails = hrService.loadUserByUsername(loginRequest.getEmail());
            boolean isValid = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
            
            if (isValid) {
                List<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

                HR hr = hrRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("HR not found with email: " + loginRequest.getEmail()));
                
                UserDetailsDTO dto = new UserDetailsDTO(hr.getEmployeeId(), loginRequest.getEmail(), true, authorities);
                return ResponseEntity.ok(dto);
            } else {
                UserDetailsDTO dto = new UserDetailsDTO(null, loginRequest.getEmail(), false, Collections.emptyList());
                return ResponseEntity.ok(dto);
            }
        } catch (UsernameNotFoundException e) {
            UserDetailsDTO dto = new UserDetailsDTO(null, loginRequest.getEmail(), false, Collections.emptyList());
            return ResponseEntity.ok(dto);
        }
    }

    @GetMapping("/notifications/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }

    @PutMapping("/notifications/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
