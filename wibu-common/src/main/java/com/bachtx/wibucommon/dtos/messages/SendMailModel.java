package com.bachtx.wibucommon.dtos.messages;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMailModel<T> {
    private String to;
    private String subject;
    private String templateName;
    private T templateData;
}
