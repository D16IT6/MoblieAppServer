package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.repositorys.ICommentRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepository implements ICommentRepository {
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
            commentList = result.stream().map(convert::convertCommentDTO).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentList;
    }

    @Override
    @Transactional
    public int addComment(CommentReceiverDTO comment,Timestamp timeComment) {
        StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("add_comment_real_time");
        storedProcedureQuery.registerStoredProcedureParameter("user_id",Integer.class,ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("content",String.class,ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("post_id",Integer.class,ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("parent_comment_id",Integer.class,ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("comment_time", Timestamp.class,ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("comment_id",Integer.class,ParameterMode.OUT);

        storedProcedureQuery.setParameter("user_id",comment.getIdUserComment());
        storedProcedureQuery.setParameter("content",comment.getContentComment());
        storedProcedureQuery.setParameter("post_id",comment.getIdPostComment());
        storedProcedureQuery.setParameter("parent_comment_id",comment.getIdParentComment());
        storedProcedureQuery.setParameter("comment_time",timeComment);
        try {
            storedProcedureQuery.execute();
            return (int) (Integer) storedProcedureQuery.getOutputParameterValue("comment_id");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public void setComment(int idPost, String content) {

    }

    @Override
    public void deleteComment(int idPost) {

    }
}
