package com.mobileapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String notificationId;
    private int postId;
    private String urlAvatarUser;
    private String fullName;
    private String type;
    private boolean isRead;
    private Timestamp timeNotification;
}
