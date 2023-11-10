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
    public int addNotification(CommentReceiverDTO commentReceiver,Timestamp timeSend,String type){
        return notificationRepository.addNotification(commentReceiver,timeSend,type);
    }
}
