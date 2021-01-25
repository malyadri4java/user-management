package com.dailycode.user.management.service;

import com.dailycode.user.management.model.ConfirmationToken;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository repository;

    public ConfirmationToken getVerificationToken(String confirmationToken) {
        return repository.findByConfirmationToken(confirmationToken);
    }

    public ConfirmationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(user, token);
        return repository.save(confirmationToken);
    }

    public void delteVerificationToken(ConfirmationToken confirmationToken) {
        repository.delete(confirmationToken);
    }
}
