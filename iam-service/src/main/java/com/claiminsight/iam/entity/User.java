package com.claiminsight.iam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private Long createdDate = System.currentTimeMillis();

    @Column(nullable = false)
    private Long updatedDate = System.currentTimeMillis();

    public enum UserRole {
        CLAIMS_ANALYST,
        CLAIMS_MANAGER,
        FRAUD_ANALYST,
        ACTUARY,
        OPERATIONS_EXECUTIVE,
        ADMIN
    }
}

