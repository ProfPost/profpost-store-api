package com.Profpost.service.impl;

import com.Profpost.exception.ResourceNotFoundException;
import com.Profpost.integration.notification.email.dto.Mail;
import com.Profpost.integration.notification.email.service.EmailService;
import com.Profpost.model.entity.PasswordResetToken;
import com.Profpost.model.entity.User;
import com.Profpost.repository.PasswordResetTokenRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Transactional
    @Override
    public void createAndSendPasswordResetToken(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiration(1);

        passwordResetTokenRepository.save(passwordResetToken);

        Map<String, Object> model = new HashMap<>();
        String resetUrl = "https://profpost-web-v1.netlify.app/auth/forgot/" + passwordResetToken.getToken();
        model.put("user", user.getEmail());
        model.put("resetUrl", resetUrl);

        Mail mail = emailService.createMail(
                user.getEmail(),
                "Restablecer Contraseña",
                model,
                mailFrom
        );

        emailService.sendMail(mail, "email/password-reset-template");
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token no encontrado con token: " + token));
    }

    @Override
    public void removePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public boolean isValidToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .filter(t->!t.isExpired())
                .isPresent();
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .filter(t->!t.isExpired())
                .orElseThrow(() -> new ResourceNotFoundException("Token no encontrado con token: " + token));

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
    }
}
