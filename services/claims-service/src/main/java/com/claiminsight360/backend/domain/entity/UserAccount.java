package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "claim_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
}
