package com.bachtx.mailsenderservice.services.impl;

import com.bachtx.mailsenderservice.exceptions.SendTemplateMailFailException;
import com.bachtx.mailsenderservice.services.IMailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

@Service
@AllArgsConstructor
@Log4j2
public class MailSenderServiceImpl implements IMailSenderService {
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfiguration;

    @Override
    public void sendSimpleMessage(String sendTo, String subject, String content) {
        sendMessage(sendTo, subject, content);
    }

    @Override
    public void sendMessageWithFreemarkerTemplate(String sendTo, String subject, String templateName, Object templateDataObject) {
        log.debug("start send email");
        StringWriter htmlTemplate = new StringWriter();
        Gson gson = new Gson();
        try {
            HashMap<?, ?> templateData = new ObjectMapper().readValue(gson.toJson(templateDataObject), HashMap.class);
            freemarkerConfiguration.getTemplate(templateName + ".ftlh").process(templateData, htmlTemplate);
            sendHtmlMessage(sendTo, subject, htmlTemplate.toString());
            log.debug("end send email");
        } catch (TemplateException | MessagingException | IOException e) {
            log.error(e.getMessage(), e);
            throw new SendTemplateMailFailException(e);
        }
    }

    private void sendHtmlMessage(String sendTo, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
        mimeMessageHelper.setFrom("honggha@wibu.com");
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    private void sendMessage(String sendTo, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);
    }
}
