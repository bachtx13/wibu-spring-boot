package com.bachtx.mailsenderservice.messages.consumers;

import com.bachtx.mailsenderservice.exceptions.SendTemplateMailFailException;
import com.bachtx.mailsenderservice.services.IMailSenderService;
import com.bachtx.wibucommon.dtos.messages.SendMailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaConsumer {
    private final IMailSenderService mailSenderService;
    @KafkaListener(
            topics = "send-mail",
            groupId = "mail",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenGroupFoo(
            @Payload SendMailModel<?> message
    ) {
        if(message.getTemplateData() instanceof String){
            mailSenderService.sendSimpleMessage(
                    message.getTo(),
                    message.getSubject(),
                    (String) message.getTemplateData()
            );
        } else{
            try{
                mailSenderService.sendMessageWithFreemarkerTemplate(
                        message.getTo(),
                        message.getSubject(),
                        message.getTemplateName(),
                        message.getTemplateData()
                );
            } catch (SendTemplateMailFailException e){
                log.error(e.getMessage(), e);
                mailSenderService.sendSimpleMessage(
                        message.getTo(),
                        message.getSubject(),
                        message.getTemplateData().toString()
                );
            }
        }
    }
}
