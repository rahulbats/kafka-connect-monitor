package com.connect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${smtp.host}")
    private String smtpHost;
    @Value("${smtp.port}")
    private int smtpPort;

    @Value("${smtp.auth:false}")
    private boolean smtpAuth;

    @Value("${smtp.username}")
    private String smtpUsername;

    @Value("${smtp.password}")
    private String smtpPassword;

    @Bean(name="mailSender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        if(smtpAuth) {
            mailSender.setUsername(smtpUsername);
            mailSender.setPassword(smtpPassword);
        }


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        /*props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");*/

        return mailSender;
    }
}
