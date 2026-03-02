package com.claiminsight360.identity.repo;

import com.claiminsight360.identity.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
}
