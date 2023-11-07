package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.entitys.Notification;
import com.mobileapp.entitys.Post;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.INotificationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private final INotificationRepository notificationRepository;
    public NotificationService(@Autowired INotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
    }
    public List<NotificationDTO> getListNotification(int userId,int startGetter){
        return notificationRepository.getListNotification(userId,startGetter);
    }
    public void addNotification(CommentReceiverDTO commentReceiver,Timestamp timeComment){
        Notification notification=new Notification();
        notification.setRead(false);
        notification.setHide(false);
        notification.setTimeNotification(timeComment);
        notification.setType("COMMENT");

        Post post=new Post();
        post.setPostId(commentReceiver.getIdPostComment());
        notification.setPostNotification(post);

        User user=new User();
        user.setUserId(commentReceiver.getIdReceiverUser());
        notification.setUserReceiver(user);

        User userSender=new User();
        userSender.setUserId(commentReceiver.getIdUserComment());
        notification.setUserSender(userSender);
        notificationRepository.addNotification(notification);
    }
}
