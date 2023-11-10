package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.ProfileDTO;
import com.mobileapp.DTO.UserDTO;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.ImplClass.UserRepository;
import com.mobileapp.utils.ConvertUser;
import com.mobileapp.utils.EncryptPassword;
import com.mobileapp.utils.TypeUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final EncryptPassword encryptPassword;
    private final UserRepository userRp;
    public UserService(@Autowired UserRepository userRp){
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
        User user= ConvertUser.convertUserDTOToUserRes(userDTO);
        return userRp.saveEntity(user);
    }
    public void deleteData(){
        userRp.DeleteData();
    }

    public ProfileDTO getProfileById(int userId){
        return userRp.getProfile(userId);
    }
    public ProfileDTO getInformation(int userId){
        return userRp.getInformation(userId);
    }
    public ResponseEntity<String> updateProfile(int userId, String content, String type) {
        return switch (type.toUpperCase().trim()) {
            case TypeUpdate.FULL_NAME -> userRp.updateFullName(userId, content);
            case TypeUpdate.EMAIL -> userRp.updateEmail(userId, content);
            case TypeUpdate.DESCRIBE -> userRp.updateDescribe(userId, content);
            case TypeUpdate.URL_AVATAR -> userRp.updateUrlAvtar(userId, content);
            case TypeUpdate.PASS_WORD -> userRp.updatePassword(userId, content);
            case TypeUpdate.USER_NAME -> userRp.updateUserName(userId, content);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        };
    }


}
