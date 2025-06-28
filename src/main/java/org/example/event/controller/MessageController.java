package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.MessageDTO;
import org.example.event.entity.Message;
import org.example.event.service.interfaces.MessageService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Util.MESSAGE_PATH)
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        return ResponseEntity.ok(messageService.send(message));
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> getChat(@RequestParam Long user1, @RequestParam Long user2) {

        return ResponseEntity.ok(messageService.getChat(user1, user2));

    }
}
