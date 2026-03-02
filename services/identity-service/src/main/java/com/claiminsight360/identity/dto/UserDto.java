package com.claiminsight360.identity.dto;

import com.claiminsight360.identity.domain.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        Long userId,
        @NotBlank String name,
        RoleType role,
        @Email String email,
        String phone
) {
}
