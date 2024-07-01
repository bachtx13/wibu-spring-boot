package com.bachtx.mailsenderservice.services;

public interface IMailSenderService {
    void sendSimpleMessage(String sendTo, String subject, String content);

    void sendMessageWithFreemarkerTemplate(String sendTo, String subject, String templateName, Object templateDataObject);
}
