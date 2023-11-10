package com.mobileapp.controllers;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.DTO.PostDTO;
import com.mobileapp.entitys.Post;
import com.mobileapp.services.IPostService;
import com.mobileapp.services.ImplClass.CommentService;
import com.mobileapp.utils.SaveImageToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private final SaveImageToServer saveImage;

    private final IPostService postService;
    private final CommentService commentService;

    public PostController(@Autowired IPostService postService, @Autowired CommentService commentService) {
        saveImage = new SaveImageToServer();
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/get-post-data")
    public List<PostDTO> home(@RequestParam(value = "userId", required = false) String userId,
                              @RequestParam(value = "pageCurrent", required = false, defaultValue = "0") String pageCurrent) {
        int startGetter = Integer.parseInt(pageCurrent.trim()) * 7;
        System.out.println(pageCurrent);
        System.out.println(startGetter);
        System.out.println("id" + userId);
        List<PostDTO> postDTOList = postService.getData(userId, startGetter);
        System.out.println(postDTOList.size());
        System.out.println("ket thuc");
        return postDTOList;
    }

    @PostMapping("/save-new-post")
    public String savePost(@RequestParam(name = "content") String content, @RequestParam(name = "userId") String userId,
                           @RequestParam(name = "image") MultipartFile img, @RequestParam(name = "url") String url) {
        String  urlImage = saveImage.saveImage(img, url);
        postService.addNewPost(userId, content, urlImage);
        return "";
    }
    @PostMapping("/save-new-post-no-img")
    public String savePostNoImage(@RequestParam(name = "content") String content
            , @RequestParam(name = "userId") String userId) {
        String urlImage = "";
        postService.addNewPost(userId, content, urlImage);
        return "";
    }

    @GetMapping("/get-list-image")
    public List<Post> getListImage(@RequestParam(name = "paramUserId") String userId) {
        return postService.getListImage(userId);
    }

    @GetMapping("/get-list-post-profile")
    public List<PostDTO> getListPostInProfile(@RequestParam(name = "userIdProfile") int userId,
                                              @RequestParam(name = "startGetter") int startGetter) {
        startGetter = startGetter * 7;
        postService.getListPostProfile(userId, startGetter).forEach(e -> System.out.println(e.getIdPost()));
        return postService.getListPostProfile(userId, startGetter);
    }

    @GetMapping("/get-comment")
    public List<CommentDTO> getComment(@RequestParam(name = "postId") int postId, @RequestParam(name = "startGetter") int startGetter) {
        startGetter *= 25;
        return commentService.getListComment(postId, startGetter);
    }
    @GetMapping("/get-post-detail")
    public PostDTO getPostDetail(@RequestParam(name = "postId")int postId,@RequestParam(name = "userId")int userId){
        PostDTO postDTO=postService.getPostDetail(postId,userId);
        return postService.getPostDetail(postId,userId);
    }
}
