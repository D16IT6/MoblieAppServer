package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.entitys.Comment;
import com.mobileapp.repositorys.ICommentRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepository implements ICommentRepository<Comment> {
    @PersistenceContext
    private EntityManager entityManager;
    private final ConvertData convert ;
    List<CommentDTO> commentList = new ArrayList<>();
    public CommentRepository() {
        convert = new ConvertData();
    }

    @Transactional
    @Override
    public List<CommentDTO> getListComment(int postId, int startGetter) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getComment");
        query.registerStoredProcedureParameter("post_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("start_getter", Integer.class, ParameterMode.IN);
        query.setParameter("post_id", postId);
        query.setParameter("start_getter", startGetter);
        try {
            List<Object[]> result = query.getResultList();
//            commentList = result.stream().map(objects -> {
//                return convert.convertCommentDTO(objects);
//            }).collect(Collectors.toList());
            commentList = result.stream().map(convert::convertCommentDTO).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentList;
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void setComment(int idPost, String content) {

    }

    @Override
    public void deleteComment(int idPost) {

    }
}
