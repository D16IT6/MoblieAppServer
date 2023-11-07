package com.mobileapp.entitys;

import com.mobileapp.entitys.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Friend {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "friendId", nullable = false, length = 30)
    private int friendId;
    @ManyToOne
    @JoinColumn(name = "SenderUserID")
    private User senderUser;
    @ManyToOne
    @JoinColumn(name = "ReceiverUserID")
    private User receiverUser;
    @Basic
    @Column(name = "IsFriend", nullable = true)
    private Boolean isFriend;
    @Basic
    @Column(name = "IsPending", nullable = true)
    private Boolean isPending;
    @Basic
    @Column(name = "timeIsSend", nullable = true)
    private Timestamp timeIsSend;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
    public Friend(int senderUser,int receiverUser){
        User user=new User();
        user.setUserId(senderUser);
        User userRereceiverUser=new User();
        userRereceiverUser.setUserId(receiverUser);
        this.senderUser=user;
        this.receiverUser=userRereceiverUser;
        this.isFriend=false;
        this.isPending=true;
        this.timeIsSend=new Timestamp(System.currentTimeMillis());
    }
}
