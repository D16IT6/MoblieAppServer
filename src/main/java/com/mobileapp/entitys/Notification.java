package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "notification_id", nullable = false)
    private int notificationId;
    @ManyToOne
    @JoinColumn(name ="user_Id_Sender" )
    private User userSender;
    @ManyToOne
    @JoinColumn(name ="user_Id_Receiver" )
    private User userReceiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post postNotification;
    @Basic
    private Timestamp timeNotification;
    @Basic
    @Column(name = "is_read", nullable = true)
    private boolean isRead;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
    @Basic
    @Column(name = "type", nullable = true)
    private String type;
}
