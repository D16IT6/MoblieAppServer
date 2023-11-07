package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Post {
    @OneToMany(mappedBy = "postBeenRead")
    private Set<PostReaded> userReadPost;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "postId", nullable = false)
    private int postId;
    @Basic
    @Column(name = "content", nullable = true, length = 255,columnDefinition = "nvarchar(255)")
    private String content;
    @Basic
    @Column(name = "imageUrl", nullable = true, length = 100,columnDefinition = "nvarchar(255)")
    private String imageUrl;
    @Basic
    @Column(name = "postTime", nullable = true)
    private Timestamp postTime;
    @Basic
    @Column(name = "postLike", nullable = true)
    private Integer postLike;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
    @OneToMany(mappedBy = "postComment")
    private Set<Comment> commentSet;
    @ManyToOne
    @JoinColumn(name = "id_user_create")
    private User userCreate;
    @OneToMany(mappedBy = "PostIsUserLike")
    private Set<UserLikePost> userLikePost;
    @OneToMany(mappedBy = "postNotification")
    private Set<Notification> setNotification;
    public Post(User user,String content,String imageUrl,Timestamp postTime){
        this.content=content;
        this.postLike=0;
        this.postTime=postTime;
        this.imageUrl=imageUrl;
        this.userCreate=user;
    }
}
