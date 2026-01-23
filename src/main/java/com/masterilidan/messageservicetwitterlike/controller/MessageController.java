package com.masterilidan.messageservicetwitterlike.controller;

import com.masterilidan.messageservicetwitterlike.config.UrlConfigurer;
import com.masterilidan.messageservicetwitterlike.dto.MessageDto;
import com.masterilidan.messageservicetwitterlike.entity.Message;
import com.masterilidan.messageservicetwitterlike.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
public class MessageController {

    private final UrlConfigurer urlConfigurer;

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageRepository messageRepository;
    MessageController(UrlConfigurer urlConfigurer, MessageRepository messageRepository) {
        this.urlConfigurer = urlConfigurer;
        this.messageRepository = messageRepository;
    }
    @PostMapping("/messages")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        log.debug("post messageDto: {}", messageDto);
        Message message = new Message();
        //TODO: прийти к единому часовому поясу
        message.setCreatedOn(Timestamp.from(Instant.now()));
        message.setBody(messageDto.getBody());

        if (!validateUser(messageDto.getCreatedBy())) {
            log.debug("user not valid: {}", messageDto.getCreatedBy());
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        }

        message.setCreatedBy(messageDto.getCreatedBy());
        messageRepository.save(message);
        messageDto.setId(message.getId());
        log.debug("message saved: {}", messageDto);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    private boolean validateUser(long createdBy) {
        log.debug("validating user: {}", createdBy);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .uri(URI.create("http://" + urlConfigurer.getBaseAddrUserService() + "/user" + createdBy))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("status code form ums: {}", response.statusCode());
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            log.error("Error validating user: {}", e.getMessage());
            return false;
        }
    }

    @GetMapping("/messages/user{id}")
    public ResponseEntity<Object[]> getMessagesByUser(@PathVariable long id) {
        log.debug("get messages by user id: {}", id);
        List<Message> allByCreatedBy = messageRepository.findAllByCreatedBy(id);
        log.debug("found messages by user id: {}", allByCreatedBy.size());
        return new ResponseEntity<>(allByCreatedBy.toArray(), HttpStatus.OK);
    }

    @GetMapping("/messages/users/{userId}/page/{page}")
    public ResponseEntity<Object[]> getMessagesByUserPageable(@PathVariable long userId, @PathVariable int page) {
        log.debug("get messages pageable by user id: {}, page: {}", userId, page);
        List<Message> allByCreatedBy = messageRepository.findAllByCreatedBy(userId, PageRequest.of(page, 10));
        return new ResponseEntity<>(allByCreatedBy.toArray(), HttpStatus.OK);
    }
}
