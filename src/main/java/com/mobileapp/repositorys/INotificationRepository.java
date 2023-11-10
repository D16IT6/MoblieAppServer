package com.mobileapp.repositorys;

import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.entitys.Notification;

import java.sql.Timestamp;
import java.util.List;

public interface INotificationRepository {
    public int addNotification(CommentReceiverDTO commentReceiverDTO, Timestamp timestampAdd,String type);
    public List<NotificationDTO> getListNotification(int userId, int startGetter);
}
