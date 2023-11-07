package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.ProfileDTO;
import com.mobileapp.entitys.User;
import jakarta.persistence.*;
import org.hibernate.query.NativeQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class UserRepositorys {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity saveEntity(User user) {
        try {

            Query query=entityManager.createQuery("select u from User as u where u.userName=:userName");
            query.setParameter("userName",user.getUserName());
            List<User> users=query.getResultList();
            if(users.size()==0) {
                entityManager.persist(user);
                return ResponseEntity.status(HttpStatus.OK).build();//200
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
        }

    }

    @Transactional
    public ResponseEntity<String> updateEntity(User user) {
        try {
            User userDb=entityManager.find(User.class,user.getUserId());
            if(userDb!=null){
                if(!user.getEmail().equalsIgnoreCase("")){
                    userDb.setEmail(user.getEmail());
                }
                if (!user.getDescribe().equalsIgnoreCase("")){
                    userDb.setDescribe(user.getDescribe());
                }
                if(!user.getFullName().equalsIgnoreCase("")){
                    userDb.setFullName(user.getFullName());
                }
                if(!user.getUrlAvata().equalsIgnoreCase("")){
                    userDb.setUrlAvata(user.getUrlAvata());
                }
                entityManager.merge(user);
                 return ResponseEntity.ok("Cập nhật thành công");
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error"+ e.getMessage());

        }
    }

    @Transactional
    public Set<User> getListUser() {
        NativeQuery<User> nativeQuery = (NativeQuery<User>) entityManager.createNativeQuery("select * from [user]");
        Set<User> listUser = new HashSet<>(nativeQuery.getResultList());
        return listUser;
    }

    @Transactional
    public void DeleteData() {
        NativeQuery<User> nativeQuery = (NativeQuery<User>) entityManager.createNativeQuery("delete * from [user]");
        nativeQuery.executeUpdate();
    }

    @Transactional
    public User getUser(String username, String pass) {
        Query query = entityManager
                .createQuery("select u.fullName,u.urlAvata,u.email,u.userId from User u" +
                        " where userName =:username and passWord=:password");
        query.setParameter("username", username);
        query.setParameter("password", pass);
        User user =null;
        Set<Objects[]> listUser = new HashSet<>(query.getResultList());
        if (listUser.iterator().hasNext()) {
            Object[] result = listUser.iterator().next();
            user=new User();
            user.setFullName(result[0].toString());
            user.setUrlAvata(result[1].toString());
            user.setEmail(result[2].toString());
            user.setUserId(Integer.parseInt(result[3].toString().trim()));
            return user;
        }
        return user;
    }
    @Transactional
    public ProfileDTO getProfile(int userId){
        StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("getInforUser");
        storedProcedureQuery.registerStoredProcedureParameter("userId",Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("userId",userId);
        ProfileDTO profileDTO=new ProfileDTO();
        try {
            List<Object[]> result=storedProcedureQuery.getResultList();
            result.stream().map(object->{
                profileDTO.setFullName((String) object[1]);
                profileDTO.setUrlAvatar((String) object[2]);
                profileDTO.setEmail((String) object[3]);
                profileDTO.setAmountPost((int) object[4]);
                profileDTO.setAmountFriend((int) object[5]);
                return profileDTO;
            }).findFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return profileDTO;
    }
}
