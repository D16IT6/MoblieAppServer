package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.FriendDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.entitys.Friend;
import com.mobileapp.repositorys.IFriendRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FriendRepository implements IFriendRepository{
    private final ConvertData convertData;
    public FriendRepository(){
        convertData=new ConvertData();
    }
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void sendInvitation(Friend friend) {

    }

    @Override
    public void acceptInvitation(int friendId) {

    }

    @Override
    public void deleteInvitation(int friendId) {

    }

    @Override
    public void deleteFriend(int idFriend) {

    }

    @Override
    public List<FriendDTO> getListFriend(int userId, int startGetter) {
        Query query=entityManager.createQuery("select f from Friend as f where isFriend=true and (f.receiverUser.userId=:userId or f.senderUser.userId=:userId)");
        query.setParameter("userId",userId);
        query.setMaxResults(20);
        query.setFirstResult(startGetter);
        List<FriendDTO> friendDTOList=new ArrayList<>();
        try {
            List<Friend> friendList=query.getResultList();
            friendList.forEach(e->{
                FriendDTO friendDTO=new FriendDTO();
                friendDTO.setFriendInvitationId(e.getFriendId());
                if(e.getReceiverUser().getUserId()!=userId){
                    friendDTO.setFullName(e.getReceiverUser().getFullName());
                    friendDTO.setUserId(e.getReceiverUser().getUserId());
                    friendDTO.setUrlAvatar(e.getReceiverUser().getUrlAvata());
                }
                else {
                    friendDTO.setFullName(e.getSenderUser().getFullName());
                    friendDTO.setUserId(e.getSenderUser().getUserId());
                    friendDTO.setUrlAvatar(e.getSenderUser().getUrlAvata());
                }
                friendDTOList.add(friendDTO);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return friendDTOList;
    }

    @Override
    public List<FriendNoConfirmDTO> getListSenderFriend(int userId, int startGetter) {
        String nameStoredProcedure="getSenderFriend";
        return getListFriendNoAccept(nameStoredProcedure,userId,startGetter);
    }

    @Override
    public List<FriendNoConfirmDTO> getListReceiverFriend(int userId, int startGetter) {
        String nameStoredProcedure="getReceiverFriend";
        return getListFriendNoAccept(nameStoredProcedure,userId,startGetter);
    }
    private List<FriendNoConfirmDTO> getListFriendNoAccept(String nameStoredProcedure, int userId, int startGetter){
        StoredProcedureQuery querry=entityManager.createStoredProcedureQuery(nameStoredProcedure);
        querry.registerStoredProcedureParameter("userId",Integer.class, ParameterMode.IN);
        querry.registerStoredProcedureParameter("startGetter",Integer.class, ParameterMode.IN);
        querry.setParameter("userId",userId);
        querry.setParameter("startGetter",startGetter);
        List<FriendNoConfirmDTO> friendNoConfirmDTOList =new ArrayList<>();
        try {
            List<Object[]> dataFromDB=querry.getResultList();
            friendNoConfirmDTOList =dataFromDB.stream().map(convertData::convertFriendNoConfirm).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return friendNoConfirmDTOList;
    }
}
