package org.example.event.service.interfaces;

import org.example.event.dtos.req.MessageDTO;
import org.example.event.entity.Message;

import java.util.List;

public interface MessageService {
    String send(MessageDTO message);

    List<Message> getChat(String user1, String user2);


}
