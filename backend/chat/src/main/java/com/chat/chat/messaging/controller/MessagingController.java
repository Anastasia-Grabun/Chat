package com.chat.chat.messaging.controller;

import com.chat.chat.messaging.dto.MessageDto;
import com.chat.chat.messaging.dto.Response;
import com.chat.chat.messaging.model.Conversation;
import com.chat.chat.messaging.model.Message;
import com.chat.chat.messaging.service.MessagingService;
import com.chat.chat.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messaging")
public class MessagingController {

    private final MessagingService messagingService;

    @GetMapping("/conversations")
    public List<Conversation> getConversations(@RequestAttribute("authenticatedUser") User user) {
        return messagingService.getConversationsOfUser(user);
    }

    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@RequestAttribute("authenticatedUser") User user, @PathVariable Long conversationId) {
        return messagingService.getConversation(user, conversationId);
    }

    @PostMapping("/conversations")
    public Conversation createConversationAndAddMessage(@RequestAttribute("authenticatedUser") User sender, @RequestBody MessageDto messageDto) {
        return messagingService.createConversationAndAddMessage(sender, messageDto.receiverId(), messageDto.content());
    }

    @PostMapping("/conversations/{conversationId}/messages")
    public Message addMessageToConversation(@RequestAttribute("authenticatedUser") User sender, @RequestBody MessageDto messageDto, @PathVariable Long conversationId) {
        return messagingService.addMessageToConversation(conversationId, sender, messageDto.receiverId(),
                messageDto.content());
    }

    @PutMapping("/conversations/messages/{messageId}")
    public Response markMessageAsRead(@RequestAttribute("authenticatedUser") User user, @PathVariable Long messageId) {
        messagingService.markMessageAsRead(user, messageId);
        return new Response("Message marked as read");
    }

}
