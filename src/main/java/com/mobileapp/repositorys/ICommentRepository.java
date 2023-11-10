package com.mobileapp.repositorys;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.DTO.CommentReceiverDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ICommentRepository {
    List<CommentDTO> getListComment(int postId, int startGetter);
    int addComment(CommentReceiverDTO t, Timestamp timeComment);
    void setComment(int idPost,String content);
    void deleteComment(int idPost);
}
