package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "messageId", nullable = false, length = 30)
    private int messageId;
    @ManyToOne
    @JoinColumn(name = "conversationsId")
    private Conversations conversationsMessage;
    @Basic
    @Column(name = "content", nullable = true, length = 2147483647,columnDefinition = "nvarchar(max)")
    private String content;
    @ManyToOne
    @JoinColumn(name = "SenderUserID")
    private Participants senderUser;
    @Basic
    @Column(name = "Timestamp", nullable = true)
    private Timestamp timestamp;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
}
