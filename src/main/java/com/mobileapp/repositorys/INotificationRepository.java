package com.mobileapp.repositorys;

import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.entitys.Notification;

import java.util.List;

public interface INotificationRepository {
    public void addNotification(Notification notification);
    public List<NotificationDTO> getListNotification(int userId, int startGetter);
}
