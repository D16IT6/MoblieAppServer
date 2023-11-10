package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.repositorys.ICommentRepository;
import com.mobileapp.utils.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentService {
    private ICommentRepository commentRepository;
    private final ConvertData convertData;
    public CommentService(@Autowired ICommentRepository commentRepository){
        this.commentRepository=commentRepository;
        this.convertData=new ConvertData();
    }
    public List<CommentDTO> getListComment(int postId, int startGetter){
        return commentRepository.getListComment(postId,startGetter);
    }
    public CommentDTO addComment(CommentReceiverDTO commentReceiverDTO,Timestamp timeComment){
        int idComment=  commentRepository.addComment(commentReceiverDTO,timeComment);
        return convertData.convertCommentReceiverDTOToCommentDTO(commentReceiverDTO,timeComment,idComment);
    }
}
