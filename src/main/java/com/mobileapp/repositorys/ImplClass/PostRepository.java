package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.PostDTO;
import com.mobileapp.entitys.Post;
import com.mobileapp.repositorys.IPostRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PostRepository implements IPostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private ConvertData convertData;

    public PostRepository(@Autowired ConvertData convertData) {
        this.convertData=convertData;
    }
    @Override
    @Transactional
    public boolean addNewPost(Post post) {
        try {
            entityManager.persist(post);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean detetePost(int postId) {
        try {
            StoredProcedureQuery deletePost = entityManager.createStoredProcedureQuery("deletePost");
            deletePost.registerStoredProcedureParameter("post_id", Integer.class, ParameterMode.IN);
            deletePost.setParameter("post_id", postId);
            deletePost.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    @Transactional
    public List<PostDTO> getPostData(int userId, int startGetter) {
        StoredProcedureQuery getListPostData = storedProcedureQueryPost(userId,startGetter);
        List<PostDTO> postDTOList = new ArrayList<>();
        try {
            Set<Object[]> getPostData = new HashSet<>(getListPostData.getResultList());
            getPostData.forEach(objects -> {
                PostDTO postDTO = convertData.convertToPostDTO(objects);
                postDTOList.add(postDTO);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTOList;
    }

    @Override
    @Transactional
    public List<PostDTO> getListPostProfile(int userId, int startGetter) {
        StoredProcedureQuery getListPostData = entityManager.createStoredProcedureQuery("getPostDataProfile");
        getListPostData.registerStoredProcedureParameter("user_id", Integer.class, ParameterMode.IN);
        getListPostData.registerStoredProcedureParameter("start_getter", Integer.class, ParameterMode.IN);
        getListPostData.setParameter("user_id", userId);
        getListPostData.setParameter("start_getter", startGetter);
        List<PostDTO> postDTOList = new ArrayList<>();
        try {
            List<Object[]> result = getListPostData.getResultList();
            postDTOList=result.stream().map(objects -> {
                PostDTO postDTO = convertData.convertToPostDTO(objects);
                return postDTO;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTOList;
    }

    private StoredProcedureQuery storedProcedureQueryPost(int userId,int startGetter){
        StoredProcedureQuery getListPostData = entityManager.createStoredProcedureQuery("getPostData");
        getListPostData.registerStoredProcedureParameter("user_id", Integer.class, ParameterMode.IN);
        getListPostData.registerStoredProcedureParameter("start_getter", Integer.class, ParameterMode.IN);
        getListPostData.setParameter("user_id", userId);
        getListPostData.setParameter("start_getter", startGetter);
        return getListPostData;
    }
    @Override
    @Transactional
    public List<Post> getListImageInPost(int userId){
        Query query=entityManager.createQuery("select p.postId,p.imageUrl from Post  as p where p.userCreate.userId=:userId and p.imageUrl!=''");
        query.setParameter("userId",userId);
        List<Post> listImage=new ArrayList<>();
        try {
            listImage=query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listImage;
    }
}
