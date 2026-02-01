package com.courseplatform.auth.service;

import com.courseplatform.auth.dto.*;
import com.courseplatform.user.entity.User;
import com.courseplatform.auth.jwt.JwtUtil;
import com.courseplatform.auth.repository.UserRepository;
import com.courseplatform.common.exception.ConflictException;
import com.courseplatform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid email or password")
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getEmail(),
                jwtUtil.getExpirationSeconds()
        );
    }
}
