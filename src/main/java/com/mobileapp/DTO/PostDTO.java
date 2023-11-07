package com.mobileapp.DTO;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {
    private int idPost;
    private String content;
    private String urlImagePost;
    private Timestamp timePost;
    private int postLike;
    private int userIdCreatePost;
    private String fullNameCreatePost;
    private String urlAvataCreatePost;
    private int amountComment;
    private int amountLike;
    private Integer isLikePost;
}
