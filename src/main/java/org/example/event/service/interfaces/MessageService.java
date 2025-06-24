package org.example.event.service.interfaces;

import org.example.event.dtos.MessageDTO;
import org.example.event.entity.Message;

import java.util.List;

public interface MessageService {
    String send(MessageDTO message);

    List<Message> getChat(Long user1, Long user2);
}
