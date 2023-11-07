package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.ProfileDTO;
import com.mobileapp.DTO.UserDTO;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.ImplClass.UserRepositorys;
import com.mobileapp.utils.EncryptPassword;
import com.mobileapp.utils.convertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private EncryptPassword encryptPassword;
    private UserRepositorys userRp;
    public UserService(@Autowired UserRepositorys userRp){
        this.userRp= userRp;
        this.encryptPassword=new EncryptPassword();
    }


    public ResponseEntity<String> AddNewUser(String username, String email, String pass, String fullName){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName(username);
        userDTO.setFullName(fullName);
        userDTO.setEmail(email);
        userDTO.setPassWord(encryptPassword.encryptPassword(pass));
        userDTO.setUrlAvata("http://192.168.31.214:8989/upload/userDefault.jpg");
        User user=convertUser.convertUserDTOToUserRes(userDTO);
        return userRp.saveEntity(user);
    }
    public Set<User> getListUser(){
        return userRp.getListUser();
    }
    public void deleteData(){
        userRp.DeleteData();
    }

    public ProfileDTO getProfileById(int userId){
        return userRp.getProfile(userId);
    }

}
