package org.bachtx.wibuspringboot.services;

public interface MailSenderService {
    void sendSimpleMessage(String sendTo, String subject, String content);

    void sendMessageWithFreemarkerTemplate(String sendTo, String subject, String templateName, Object templateDataObject);
}
