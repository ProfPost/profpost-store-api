package com.Profpost.integration.notification.email.service;

import com.Profpost.integration.notification.email.dto.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;

    public Mail createMail(String to, String subject, Map<String, Object> model, String from) {
        Mail mail = new Mail();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setFrom(from);
        mail.setModel(model);

        return mail;
    }

    public void sendMail(Mail mail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariables(mail.getModel());

        String html = templateEngine.process(templateName, context);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        mailSender.send(message);
    }
}
