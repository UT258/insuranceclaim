package com.claiminsight.iam.dto;

import com.claiminsight.iam.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private User.UserRole role;
    private Boolean active;
    private Long createdDate;
    private Long updatedDate;
}

