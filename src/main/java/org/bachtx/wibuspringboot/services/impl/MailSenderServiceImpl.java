package org.bachtx.wibuspringboot.services.impl;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bachtx.wibuspringboot.services.MailSenderService;
import org.bachtx.wibuspringboot.utils.HashMapUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfiguration;
    private final HashMapUtil hashMapUtil;

    @Override
    public void sendSimpleMessage(String sendTo, String subject, String content) {
        sendMessage(sendTo, subject, content);
    }

    @Override
    public void sendMessageWithFreemarkerTemplate(String sendTo, String subject, String templateName, Object templateDataObject) {
        Map<String, Object> templateData = hashMapUtil.objectToHashMap(templateDataObject);
        StringWriter htmlTemplate = new StringWriter();
        try {
            freemarkerConfiguration.getTemplate(templateName + ".ftlh").process(templateData, htmlTemplate);
            sendHtmlMessage(sendTo, subject, htmlTemplate.toString());
        } catch (TemplateException | IOException | MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendHtmlMessage(String sendTo, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBody, true);
        System.out.println("here");
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
