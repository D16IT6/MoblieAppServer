package com.mobileapp.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer idParentComment;
    private int idComment;
    private int idUserComment;
    private String userNameComment;
    private String userUrlAvatar;
    private String contentComment;
    private Timestamp timeComment;
    private int amountReply;
}
