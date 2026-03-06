package org.management.service;

import org.management.dto.EmailConfigurationDto;
import org.management.entity.EmailConfiguration;
import org.management.repository.EmailConfigurationRepository;
import org.management.utils.AESUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.management.utils.ResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailConfigurationService {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailConfigurationRepository repository;

    public List<EmailConfiguration> list() {
        List<EmailConfiguration> result = repository.findAll(Sort.by(Sort.Direction.ASC, "userName"));
        return result;
    }

    @SneakyThrows
    public EmailConfiguration add(EmailConfigurationDto emailConfigurationDto) {
        EmailConfiguration emailConfiguration = this.modelMapper.map(emailConfigurationDto, EmailConfiguration.class);
        emailConfiguration.setEmailId(1);
        emailConfiguration.setAccountPassword(AESUtil.encrypt(emailConfigurationDto.getAccountPassword()));
        repository.save(emailConfiguration);
        return emailConfiguration;
    }

    @SneakyThrows
    public ResponseModel update(EmailConfigurationDto emailConfigurationDto, Integer id) {
        Optional<EmailConfiguration> optional = repository.findById(id);
        if (optional.isPresent()) {
            EmailConfiguration emailConfiguration = optional.get();
            this.modelMapper.map(emailConfigurationDto, emailConfiguration);
            emailConfiguration.setAccountPassword(AESUtil.encrypt(emailConfigurationDto.getAccountPassword()));
            repository.save(emailConfiguration);
            return new ResponseModel(true, "Updated Successfully", emailConfiguration);
        }
        throw new RuntimeException("Email  id invalid");
    }

    public ResponseModel delete(Integer id) {
        repository.deleteById(id);
        return new ResponseModel(true, "Deleted Successfully");
    }

    @SneakyThrows
    public ResponseModel sendEmail(String subject, String body, String toAddress) {
        Optional<EmailConfiguration> mailConfig = repository.findByActive(true);
        if (mailConfig.isPresent()) {
            String decodePassword = AESUtil.decrypt(mailConfig.get().getAccountPassword());
            Properties properties = new Properties();
            properties.put("mail.smtp.host", mailConfig.get().getSmtpServer());
            properties.put("mail.smtp.port", mailConfig.get().getSmtpPort());
            properties.put("mail.smtp.auth", mailConfig.get().getAuthentication());
            properties.put("mail.smtp.starttls.enable", mailConfig.get().getEnableTLS());
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailConfig.get().getUserName(), decodePassword);
                }
            };

            Session session = Session.getInstance(properties, authenticator);
            executorService.submit(() -> {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mailConfig.get().getUserName()));
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
                    message.setSubject(subject);
                    message.setContent(body, "text/html");
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            return new ResponseModel(true, "Mail Sent Successfully");
        } else {
            return new ResponseModel(false, "Mail configuration not found");
        }
    }


    @SneakyThrows
    public ResponseModel sendEmailWithSummaryData(String to, String subject, String summaryData) {
        Optional<EmailConfiguration> mailConfig = repository.findByActive(true);
        if (mailConfig.isPresent()) {
            String decodePassword = AESUtil.decrypt(mailConfig.get().getAccountPassword());
            Properties properties = new Properties();
            properties.put("mail.smtp.host", mailConfig.get().getSmtpServer());
            properties.put("mail.smtp.port", mailConfig.get().getSmtpPort());
            properties.put("mail.smtp.auth", mailConfig.get().getAuthentication());
            properties.put("mail.smtp.starttls.enable", mailConfig.get().getEnableTLS());

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailConfig.get().getUserName(), decodePassword);
                }
            };

            Session session = Session.getInstance(properties, authenticator);
            executorService.submit(() -> {
                try {
                    MimeMessage message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(mailConfig.get().getUserName()));
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject(subject);
                    message.setContent(summaryData, "text/html");

                    Transport.send(message);
                    System.out.println("Email sent successfully to: " + to);

                } catch (MessagingException e) {
                    System.err.println("Error sending email: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            return new ResponseModel(true, "Mail Sent Successfully");
        } else {
            return new ResponseModel(false, "Mail configuration not found");
        }
    }
}