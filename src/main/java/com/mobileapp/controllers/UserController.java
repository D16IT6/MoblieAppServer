package com.mobileapp.controllers;

import com.mobileapp.DTO.ProfileDTO;
import com.mobileapp.DTO.responseLogin;
import com.mobileapp.services.ImplClass.LoginService;
import com.mobileapp.services.ImplClass.UserService;
import com.mobileapp.utils.SaveImageToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {
//    @RequestParam(name = "userName")String userName,@RequestParam(name = "passWord")String password
    private final LoginService loginService;

    private final UserService userService;
    private final SaveImageToServer saveImage;

    public UserController(@Autowired LoginService loginService,@Autowired UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
        this.saveImage = new SaveImageToServer();
    }

    @PostMapping("/login-username")
    public responseLogin login(@RequestParam(name = "userName")String userName, @RequestParam(name = "passWord")String password) {
        return loginService.login(userName, password);
    }
    @GetMapping("/get-profile")
    public ProfileDTO getProfile(@RequestParam(name = "userId")int userId){
        return userService.getProfileById(userId);
    }
    @PostMapping("/register-account")
    public ResponseEntity<String> registerAccount(@RequestParam(name = "userName")String userName,
                                                  @RequestParam(name = "email")String email,
                                                  @RequestParam(name = "password")String password,
                                                  @RequestParam(name = "fullname")String fullname){
        return userService.AddNewUser(userName,email,password,fullname);
    }
    @GetMapping("/get-infor")
    public ProfileDTO getInformation(@RequestParam(name = "userId")int userId){
        return userService.getInformation(userId);
    }
    @PostMapping("/update-profile")
    public ResponseEntity<String> updateProfile(
            @RequestParam(name = "userId")int userId,
            @RequestParam(name = "content")String content,
            @RequestParam(name = "type")String type
    ){
        return userService.updateProfile(userId,content,type);
    }
    @PostMapping("/update-avatar")
    public String updateAvatar(
            @RequestParam(name = "userId")int userId, @RequestParam(name = "type")String type,
            @RequestParam(name = "image") MultipartFile img, @RequestParam(name = "url") String url
    ){
        String urlAvatar=saveImage.saveImage(img,url);
        userService.updateProfile(userId,urlAvatar, type);
        return urlAvatar;
    }

}
