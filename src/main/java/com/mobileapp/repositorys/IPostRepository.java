package com.mobileapp.repositorys;

import com.mobileapp.DTO.PostDTO;
import com.mobileapp.entitys.Post;

import java.util.List;

public interface IPostRepository {
    public List<PostDTO> getPostData(int userId,int startGetter);
    public boolean addNewPost(Post post);
    public boolean detetePost(int postId);
    public List<PostDTO> getListPostProfile(int userId,int startGetter);
    public List<Post> getListImageInPost(int userId);
    PostDTO getPostDetail(int postId,int userId);
    }
