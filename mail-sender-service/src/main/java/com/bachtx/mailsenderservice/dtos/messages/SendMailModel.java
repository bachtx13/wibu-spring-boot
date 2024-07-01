package com.bachtx.mailsenderservice.dtos.messages;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMailModel {
    private String to;
    private String subject;
    private String templateName;
    private Object templateData;
}
