package com.mobileapp.utils;

import com.mobileapp.DTO.UserDTO;
import com.mobileapp.entitys.Role;
import com.mobileapp.entitys.User;

public class convertUser {
    public static User convertUserDTOToUserRes(UserDTO userDTO){
        User user=new User();
        user.setUserName(userDTO.getUserName());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setUrlAvata(userDTO.getUrlAvata());
        user.setPassWord(userDTO.getPassWord());
        user.setRole(new Role("user","user",3));
        return user;
    }
}
