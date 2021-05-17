package com.univercity.vntu.registration.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    long updateConfirmedAt(String token, LocalDateTime confirmedAt);

    void save(ConfirmationToken token);
}