package com.riceroll.service.impl;

import com.riceroll.utils.MemoryStoreUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemoryStoreUtils memoryStoreUtils;

    @Value("${static.rootpath}")
    private String rootPath;

    @Value("${static.email}")
    private String emailTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${email.emailTitle}")
    private String emailTitle;

    // 发送简单文本邮件
    public void sendSimpleEmail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(emailTitle);
        message.setText(text);
        mailSender.send(message);
    }

    // 发送HTML格式邮件
    @Async
    public void sendHtmlEmail(String to, String name,String comment,String url)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(emailTitle);
        String htmlContent = getEmailTemplate()
                .replace("${name}", name)
                .replace("${comment}", comment)
                .replace("${url}", url);
        System.out.println("htmlContent = " + htmlContent);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    private String getEmailTemplate(){
        String htmlContent = (String) memoryStoreUtils.get("email");

        if(htmlContent == null || htmlContent.isEmpty()) {
            Path path = Paths.get(rootPath, emailTemplate);
            try {
                htmlContent = Files.readString(path);
                return htmlContent;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return htmlContent;
    }

    public void saveEmail(){
        Path path = Paths.get(rootPath, emailTemplate);
        try {
            String content = Files.readString(path);
            memoryStoreUtils.set("email", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
