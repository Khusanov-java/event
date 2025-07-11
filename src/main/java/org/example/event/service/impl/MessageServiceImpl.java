package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.MessageDTO;
import org.example.event.entity.Message;
import org.example.event.entity.User;
import org.example.event.repo.MessageRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public String send(MessageDTO request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        messagingTemplate.convertAndSend(
                "/topic/messages",
                request
        );
        messageRepository.save(message);
        return "Message sent successfully";
    }

    @Override
    public List<Message> getChat(String user1, String user2) {
        return messageRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                         user1, user2, user1, user2
                );
    }
}
