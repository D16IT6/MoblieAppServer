package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.PostDTO;
import com.mobileapp.entitys.Post;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.IPostRepository;
import com.mobileapp.repositorys.ImplClass.PostRepository;
import com.mobileapp.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Override
    public List<PostDTO> getData(String userId,int startGetter) {
        int id=Integer.parseInt(userId.trim());
        return postRepository.getPostData(id,startGetter);
    }
    @Override
    public boolean deletePostData(String postId) {
        return postRepository.detetePost(Integer.parseInt(postId.trim()));
    }

    @Override
    public boolean addNewPost(String userId,String content,String url) {
        User user=new User();
        user.setUserId(Integer.parseInt(userId.trim()));
        Timestamp timestamp=new Timestamp(new Date().getTime());
        Post post=new Post(user,content,url,timestamp);
        postRepository.addNewPost(post);
        return false;
    }
    public List<PostDTO> getListPostProfile(String userId,int startGetter){
        return postRepository.getListPostProfile(Integer.parseInt(userId),startGetter);
    }
    @Override
    public List<Post> getListImage(String userId){
        return postRepository.getListImageInPost(Integer.parseInt(userId.trim()));
    }
    @Override
    public List<PostDTO> getListPostProfile(int userId, int startGetter){
        return postRepository.getListPostProfile(userId,startGetter);
    }

    @Override
    public PostDTO getPostDetail(int postId, int userId) {
        return postRepository.getPostDetail(postId,userId);
    }
}
