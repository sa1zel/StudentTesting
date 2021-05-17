package com.univercity.vntu.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ApplicationUserRepository {
    Optional<ApplicationUser> findByEmail(String email);

    long enableAppUser(String email);

    void save(ApplicationUser appUser);
}
