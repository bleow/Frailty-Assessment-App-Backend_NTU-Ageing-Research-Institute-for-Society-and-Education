package com.frailty.backend.service;

import com.frailty.backend.entity.AppUser;
import com.frailty.backend.entity.ConfirmationToken;
import com.frailty.backend.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    public static final int MAX_REGISTRATION_TOKEN_VALID_MINUTES = 30;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public Optional<ConfirmationToken> getConfirmationToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public String generateConfirmationToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(MAX_REGISTRATION_TOKEN_VALID_MINUTES), appUser);
        confirmationTokenRepository.save(confirmationToken);
        return token;
    }
}
