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
public class CommentReceiverDTO {
    private Integer idParentComment;
    private int idUserComment;
    private int idPostComment;
    private int idReceiverUser;
    private String contentComment;
    private Timestamp timeComment;
    private String userNameComment;
    private String userUrlAvatar;
}
