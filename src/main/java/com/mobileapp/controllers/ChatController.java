package com.mobileapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.DTO.PostDTO;
import com.mobileapp.services.ImplClass.CommentService;
import com.mobileapp.services.ImplClass.NotificationService;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;

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
//    @SendTo("/topic/comment")
//    @SendToUser("/queue/comment")
    public void sendComment(@Payload CommentReceiverDTO commentReceiver, SimpMessageHeaderAccessor headerAccessor) throws JsonProcessingException {
        int userReceiverId = commentReceiver.getIdReceiverUser();
        Timestamp timeComment=new Timestamp(new Date().getTime());
        commentService.addComment(commentReceiver.getIdPostComment(),
                commentReceiver.getContentComment(), commentReceiver.getIdParentComment(),
                commentReceiver.getIdUserComment(),timeComment);

        if (userReceiverId != commentReceiver.getIdUserComment()) {
            notificationService.addNotification(commentReceiver,timeComment);
            commentReceiver.setTimeComment(timeComment);
            System.out.println(commentReceiver.getTimeComment());
//            String data = new ObjectMapper().writeValueAsString(commentReceiver);
//            System.out.println(data);
            messagingTemplate.convertAndSendToUser(userReceiverId + "", "/comment", commentReceiver);
        }
//        return commentReceiver;
    }

    @MessageMapping("/sender-message")
    public void sendMessage(@Payload CommentReceiverDTO commentReceiver, SimpMessageHeaderAccessor headerAccessor) {
        int userReceiverId = commentReceiver.getIdReceiverUser();
        String destination = "/user/" + userReceiverId + "/topic/message";
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, commentReceiver);
    }


}
