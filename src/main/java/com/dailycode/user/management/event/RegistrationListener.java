package com.dailycode.user.management.event;

import com.dailycode.user.management.model.ConfirmationToken;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.service.ConfirmationTokenService;
import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private ConfirmationTokenService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    @Value ("${server.port}")
    private int port;

    @Value ("${server.host}")
    private String host;

    @Value ("${user.mail.from}")
    private String fromEmail;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        ConfirmationToken confirmationToken = service.createVerificationToken(user);
        try {
            String confirmationUrl = event.getAppUrl() + String.format(ViewNames.REGISTRATION_CONFIM, confirmationToken.getConfirmationToken());
            String message = messages.getMessage("thank.message.registration", null, event.getLocale());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Successfully registration account with us!");
            mailMessage.setFrom(fromEmail);
            mailMessage.setText(buildUrl(message, confirmationUrl));

            mailSender.send(mailMessage);
        } catch (MailAuthenticationException mailEx) {
            service.delteVerificationToken(confirmationToken);
            log.error("Fail to send email.", mailEx);
            throw new RuntimeException("Unable to process the request");
        }
    }

    private String buildUrl(String message, String confirmationUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n").append(message).append("\r\n").append("\r\n");
        builder.append("\r\n").append("http").append("://").append(this.host).append(":").append(this.port).append(confirmationUrl);
        return builder.append("\r\n").append("\r\n").append("\r\n").append("Thanks").append("\r\n").append("Service Team!").toString();
    }
}
