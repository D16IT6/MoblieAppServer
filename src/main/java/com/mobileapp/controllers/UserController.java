package com.mobileapp.controllers;

import com.mobileapp.DTO.ProfileDTO;
import com.mobileapp.DTO.responseLogin;
import com.mobileapp.services.ImplClass.LoginService;
import com.mobileapp.services.ImplClass.UserService;
import com.mobileapp.utils.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
//    @RequestParam(name = "userName")String userName,@RequestParam(name = "passWord")String password
    @Autowired
    private LoginService loginService;
    @Autowired
    private ConvertData convertData;
    @Autowired
    private UserService userService;
    @PostMapping("/login-username")
    public responseLogin login(@RequestParam(name = "userName")String userName, @RequestParam(name = "passWord")String password) {
        responseLogin response=loginService.login(userName, password);
        return response;
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
}
