package com.masterilidan.messageservicetwitterlike.controller;

import com.masterilidan.messageservicetwitterlike.dto.MessageDto;
import com.masterilidan.messageservicetwitterlike.entity.Message;
import com.masterilidan.messageservicetwitterlike.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class MessageController {

    private final MessageRepository messageRepository;
    MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @PostMapping("/messages")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        Message message = new Message();
        message.setCreatedOn(Timestamp.valueOf(messageDto.getCreatedAt()));
        message.setBody(messageDto.getBody());
        message.setCreatedBy(messageDto.getCreatedBy());
        messageRepository.save(message);
        messageDto.setId(message.getId());
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }
    @GetMapping("/messages/user{id}")
    public ResponseEntity<Object[]> updateMessage(@PathVariable long id) {
        List<Message> allByCreatedBy = messageRepository.findAllByCreatedBy(id);
        return new ResponseEntity<>(allByCreatedBy.toArray(), HttpStatus.OK);
    }
}
