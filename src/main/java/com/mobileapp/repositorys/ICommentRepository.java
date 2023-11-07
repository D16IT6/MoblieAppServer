package com.mobileapp.repositorys;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.entitys.Comment;

import java.util.List;

public interface ICommentRepository <T>{
    public List<CommentDTO> getListComment(int postId, int startGetter);
    public void addComment(T t);
    public void setComment(int idPost,String content);
    public void deleteComment(int idPost);
}
