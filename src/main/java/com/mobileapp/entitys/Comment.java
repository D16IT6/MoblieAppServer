package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "commentId", nullable = false)
    private Integer commentId;
    @ManyToOne
    @JoinColumn(name ="userId" )
    private User userComment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post postComment;
    @Basic
    @Column(name = "content", nullable = true, length = 2147483647,columnDefinition = "nvarchar(max)")
    private String content;
    @Basic
    @Column(name = "commentTime", nullable = true)
    private Timestamp commentTime;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
    @ManyToOne
    @JoinColumn(name = "parentCommentId",nullable = true)
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment")
    private Set<Comment> childComments;
    public Comment(Post postComment,String content,Comment parentComment,User user,Timestamp commentTime){
        this.commentTime=commentTime;
        this.content=content;
        this.parentComment=parentComment;
        this.userComment=user;
        this.postComment=postComment;
    }
 }
