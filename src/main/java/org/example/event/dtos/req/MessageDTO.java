package org.example.event.dtos.req;

import lombok.Data;

import javax.xml.transform.sax.SAXResult;

@Data
public class MessageDTO {
    private String senderId;
    private String  receiverId;
    private String content;
}
