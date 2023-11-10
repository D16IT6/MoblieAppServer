package com.mobileapp.services;

import com.mobileapp.DTO.PostDTO;
import com.mobileapp.entitys.Post;

import java.util.List;

public interface IPostService {
    public List<PostDTO> getData(String userId,int startGetter);
    public boolean deletePostData(String postId);
    public boolean addNewPost(String userId,String content,String url);
    public List<Post> getListImage(String userId);
    public List<PostDTO> getListPostProfile(int userId, int startGetter);
    PostDTO getPostDetail(int postId,int userId);
}
