package com.claiminsight360.identity.service;

import com.claiminsight360.identity.domain.UserAccount;
import com.claiminsight360.identity.dto.UserDto;
import com.claiminsight360.identity.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserDto create(UserDto dto) {
        UserAccount user = new UserAccount();
        user.setName(dto.name());
        user.setRole(dto.role());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        return toDto(userRepository.save(user));
    }

    private UserDto toDto(UserAccount user) {
        return new UserDto(user.getUserId(), user.getName(), user.getRole(), user.getEmail(), user.getPhone());
    }
}
