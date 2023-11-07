package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.entitys.Comment;
import com.mobileapp.entitys.Post;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private ICommentRepository<Comment> commentRepository;
    public CommentService(@Autowired ICommentRepository<Comment> commentRepository){
        this.commentRepository=commentRepository;
    }
    public List<CommentDTO> getListComment(int postId, int startGetter){
        return commentRepository.getListComment(postId,startGetter);
    }
    public void addComment(int idPost,String content,Integer idParentComment,int userId,Timestamp timeComment){
        User userComment=new User();
        userComment.setUserId(userId);
        Post postCommnent=new Post();
        postCommnent.setPostId(idPost);
        if(idParentComment!=null) {
            Comment parentComment = new Comment();
            parentComment.setCommentId(idParentComment);
            Comment comment = new Comment(postCommnent, content, parentComment, userComment, timeComment);
            commentRepository.addComment(comment);
        }else {
            Comment comment = new Comment(postCommnent, content, null, userComment, timeComment);
            commentRepository.addComment(comment);
        }

    }
}
