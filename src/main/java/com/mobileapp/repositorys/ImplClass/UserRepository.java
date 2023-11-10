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
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<String> saveEntity(User user) {
        try {

            Query query = entityManager.createQuery("select u from User as u where u.userName=:userName");
            query.setParameter("userName", user.getUserName());
            List<User> users = query.getResultList();
            if (users.size() == 0) {
                entityManager.persist(user);
                return ResponseEntity.status(HttpStatus.OK).build();//200
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
        }

    }


    @Transactional
    public ResponseEntity<String> updateUserName(int userId, String userName) {
        Query query = entityManager.createQuery("UPDATE User as u SET u.userName=:data where u.userId=:userId");
        return update(query, userId, userName);
    }
    @Transactional
    public ResponseEntity<String> updateEmail(int userId,String email){
        Query query = entityManager.createQuery("UPDATE User as u SET u.email=:data where u.userId=:userId");
        return update(query, userId, email);
    }
    @Transactional
    public ResponseEntity<String> updateDescribe(int userId,String describe){
        Query query = entityManager.createQuery("UPDATE User as u SET u.describe=:data where u.userId=:userId");
        return update(query, userId, describe);
    }
    @Transactional
    public ResponseEntity<String> updateFullName(int userId,String fullName){
        Query query = entityManager.createQuery("UPDATE User as u SET u.fullName=:data where u.userId=:userId");
        return update(query, userId, fullName);
    }
    @Transactional
    public ResponseEntity<String> updateUrlAvtar(int userId,String urlAvtar){
        Query query = entityManager.createQuery("UPDATE User as u SET u.urlAvata=:data where u.userId=:userId");
        return update(query, userId, urlAvtar);
    }
    @Transactional
    public ResponseEntity<String> updatePassword(int userId,String urlAvtar){
        Query query = entityManager.createQuery("UPDATE User as u SET u.urlAvata=:data where u.userId=:userId");
        return update(query, userId, urlAvtar);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<String> checkUserName(String userName) {
        try {
            TypedQuery<Long> query = entityManager.createQuery("select count(u) from User u where u.userName = :username", Long.class);
            query.setParameter("username", userName);
            Long count = query.getSingleResult();

            if (count == 0) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
                .createQuery("select u.fullName,u.urlAvata,u.email,u.userId,u.userName,u.describe from User u" +
                        " where userName =:username and passWord=:password");
        query.setParameter("username", username);
        query.setParameter("password", pass);
        User user = null;
        Set<Objects[]> listUser = new HashSet<>(query.getResultList());
        if (listUser.iterator().hasNext()) {
            Object[] result = listUser.iterator().next();
            user = new User();
            user.setFullName(result[0].toString());
            user.setUrlAvata(result[1].toString());
            user.setEmail(result[2].toString());
            user.setUserId(Integer.parseInt(result[3].toString().trim()));
            user.setUserName(result[4].toString());
            user.setDescribe(result[5].toString());
            return user;
        }
        return user;
    }

    @Transactional
    public ProfileDTO getProfile(int userId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("getInforUser");
        storedProcedureQuery.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("userId", userId);
        ProfileDTO profileDTO = new ProfileDTO();
        try {
            List<Object[]> result = storedProcedureQuery.getResultList();
            result.stream().map(object -> {
                profileDTO.setFullName((String) object[1]);
                profileDTO.setUrlAvatar((String) object[2]);
                profileDTO.setEmail((String) object[3]);
                profileDTO.setAmountPost((int) object[4]);
                profileDTO.setAmountFriend((int) object[5]);
                profileDTO.setUserName((String) object[6]);
                profileDTO.setDescribe((String) object[7]);
                return profileDTO;
            }).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profileDTO;
    }

    @Transactional
    public ProfileDTO getInformation(int userId) {
        ProfileDTO profileDTO = new ProfileDTO();
        try {
            Query query = entityManager.createQuery("select u.userName,u.fullName,u.urlAvata,u.email,u.describe,u.userId from User as u where u.userId=:userId");
            query.setParameter("userId", userId);
            List<Object[]> objectList = query.getResultList();

            objectList.stream().map(object -> {
                profileDTO.setUserName((String) object[0]);
                profileDTO.setFullName((String) object[1]);
                profileDTO.setUserName((String) object[2]);
                profileDTO.setEmail((String) object[3]);
                profileDTO.setDescribe((String) object[4]);
                return profileDTO;
            }).findFirst();
            return profileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profileDTO;
    }

    @Transactional
    public ResponseEntity<String> update(Query query, int userId, String data) {
        try {
            query.setParameter("data", data);
            query.setParameter("userId", userId);
            query.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
