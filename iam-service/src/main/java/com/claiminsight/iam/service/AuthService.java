package com.claiminsight.iam.service;

import com.claiminsight.common.exception.BadRequestException;
import com.claiminsight.common.exception.ResourceNotFoundException;
import com.claiminsight.iam.dto.*;
import com.claiminsight.iam.entity.AuditLog;
import com.claiminsight.iam.entity.User;
import com.claiminsight.iam.repository.AuditLogRepository;
import com.claiminsight.iam.repository.UserRepository;
import com.claiminsight.iam.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .active(true)
                .createdDate(System.currentTimeMillis())
                .updatedDate(System.currentTimeMillis())
                .build();

        user = userRepository.save(user);

        // Create audit log
        createAuditLog(user.getUserId(), "USER_REGISTRATION", "User", "SUCCESS");

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId()
        );

        return AuthResponse.builder()
                .token(token)
                .user(mapToDto(user))
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!user.getActive()) {
            throw new BadRequestException("User account is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            createAuditLog(user.getUserId(), "LOGIN_FAILED", "User", "FAILURE");
            throw new BadRequestException("Invalid credentials");
        }

        // Create audit log
        createAuditLog(user.getUserId(), "USER_LOGIN", "User", "SUCCESS");

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId()
        );

        return AuthResponse.builder()
                .token(token)
                .user(mapToDto(user))
                .build();
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return mapToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByRole(User.UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(Long userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setUpdatedDate(System.currentTimeMillis());

        user = userRepository.save(user);
        createAuditLog(userId, "USER_UPDATE", "User", "SUCCESS");

        return mapToDto(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        user.setActive(false);
        user.setUpdatedDate(System.currentTimeMillis());
        userRepository.save(user);

        createAuditLog(userId, "USER_DEACTIVATION", "User", "SUCCESS");
    }

    private void createAuditLog(Long userId, String action, String resource, String status) {
        AuditLog auditLog = AuditLog.builder()
                .userId(userId)
                .action(action)
                .resource(resource)
                .status(status)
                .timestamp(System.currentTimeMillis())
                .build();
        auditLogRepository.save(auditLog);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.getActive())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .build();
    }
}

