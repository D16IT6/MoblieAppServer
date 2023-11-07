package com.mobileapp.repositorys;

import com.mobileapp.DTO.FriendDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.entitys.Friend;

import java.util.List;

public interface IFriendRepository {
    public void sendInvitation(Friend friend);
    public void acceptInvitation(int friendId);
    public void deleteInvitation(int friendId);
    public void deleteFriend(int idFriend);
    public List<FriendDTO> getListFriend(int userId, int startGetter);
    public List<FriendNoConfirmDTO> getListSenderFriend(int userId, int startGetter);
    public List<FriendNoConfirmDTO> getListReceiverFriend(int userId, int startGetter);
}
