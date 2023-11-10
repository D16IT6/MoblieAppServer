package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[user]")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId", nullable = false)
    private int userId;
    @Basic
    @Column(name = "userName", nullable = true, length = 30,columnDefinition = "nvarchar(30)")
    private String userName;
    @Basic
    @Column(name = "passWord", nullable = true, columnDefinition = "varchar(Max)")
    private String passWord;
    @Basic
    @Column(name = "fullName", nullable = true, length = 50,columnDefinition = "nvarchar(50)")
    private String fullName;
    @Basic
    @Column(name = "urlAvata", nullable = true, length = 100,columnDefinition = "nvarchar(255)")
    private String urlAvata;
    @Basic
    @Column(name = "email", nullable = true, length = 50)
    private String email;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
    @Basic
    @Column(name = "describe", nullable = true, length = 50)
    private String describe;
    @OneToMany(mappedBy = "userRead")
    private Set<PostReaded> postSet;
    @OneToMany(mappedBy = "userComment")
    private Set<Comment> commentSet;
    @OneToMany(mappedBy = "userParticipants")
    private Set<Participants> participants;
    @OneToMany(mappedBy = "senderUser")
    private Set<Friend> friendSender;
    @OneToMany(mappedBy = "receiverUser")
    private Set<Friend> friendReceiver;
    @OneToMany(mappedBy = "creatorUser")
    private Set<Conversations> conversationsSet;
    @OneToMany(mappedBy = "userCreate")
    private Set<Post> postList;
    @OneToMany(mappedBy = "userLikePost")
    private Set<UserLikePost> postLikeList;
    @OneToMany(mappedBy = "userSender")
    private Set<Notification> userSenderList;
    @OneToMany(mappedBy = "userReceiver")
    private Set<Notification> userReceiverList;
  }
