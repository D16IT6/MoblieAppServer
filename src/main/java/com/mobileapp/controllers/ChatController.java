package com.mobileapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobileapp.DTO.*;
import com.mobileapp.services.ImplClass.CommentService;
import com.mobileapp.services.ImplClass.NotificationService;
import com.mobileapp.utils.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final CommentService commentService;

    private final NotificationService notificationService;
private ConvertData convertData;
    public ChatController(@Autowired SimpMessagingTemplate messagingTemplate,@Autowired CommentService commentService,@Autowired NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.commentService = commentService;
        this.notificationService = notificationService;
        this.convertData=new ConvertData();
    }

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public String sendMessage(
            @Payload String chatMessage
    ) {
        System.out.println(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/sender-friend")
    public FriendNoConfirmDTO sendAlert(
            @Payload FriendNoConfirmDTO senderFriend
    ) {
        System.out.println(senderFriend);
        return senderFriend;
    }

    @MessageMapping("/sender-like")
    @SendTo("/topic/public")
    public PostDTO sendLike(PostDTO postDTO) {
        return postDTO;
    }

    @MessageMapping("/sender-comment")
    public void sendComment(@Payload CommentReceiverDTO commentReceiver, SimpMessageHeaderAccessor headerAccessor) throws JsonProcessingException {
        int userReceiverId = commentReceiver.getIdReceiverUser();
        Timestamp timeComment=new Timestamp(new Date().getTime());

        CommentDTO commentDTO=commentService.addComment(commentReceiver,timeComment);
        if (userReceiverId != commentReceiver.getIdUserComment()) {
            int idNotification=notificationService.addNotification(commentReceiver,timeComment,"COMMENT");
            NotificationDTO notificationDTO=convertData.convertCommentReceiverDTOToNotificationDTO(commentReceiver,idNotification,timeComment);
            messagingTemplate.convertAndSendToUser(userReceiverId + "", "/comment", notificationDTO);
        }
        messagingTemplate.convertAndSendToUser(commentReceiver.getIdUserComment()+"","/reply",commentDTO);
    }

    @MessageMapping("/sender-message")
    public void sendMessage(@Payload CommentReceiverDTO commentReceiver, SimpMessageHeaderAccessor headerAccessor) {
        int userReceiverId = commentReceiver.getIdReceiverUser();
        String destination = "/user/" + userReceiverId + "/topic/message";
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, commentReceiver);
    }


}
