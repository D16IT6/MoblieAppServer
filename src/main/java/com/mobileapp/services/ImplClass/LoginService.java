package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.responseLogin;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.ImplClass.UserRepository;
import com.mobileapp.utils.EncryptPassword;
import com.mobileapp.utils.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private JWTToken jwtToken;
    private UserRepository userRepository;

    private EncryptPassword encryptPassword;
    public LoginService(@Autowired JWTToken jwtToken, @Autowired UserRepository userRepository) {
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
        this.encryptPassword=new EncryptPassword();
    }

    public responseLogin login(String userName, String pass) {
        String passEncode=encryptPassword.encryptPassword(pass);
        User user = userRepository.getUser(userName,passEncode);
        if (user != null) {
            responseLogin response = new responseLogin();
            response.setStatus(true);
            String token=jwtToken.generateToken(user);
            response.setJwtToken(token);
            return response;
        } else {
            responseLogin response = new responseLogin();
            response.setStatus(false);
            response.setJwtToken("");
            return response;
        }
    }
}
