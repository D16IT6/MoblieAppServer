package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.FriendDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.entitys.Friend;
import com.mobileapp.repositorys.IFriendRepository;
import com.mobileapp.services.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService implements IFriendService {
    private final IFriendRepository friendRepository;
    public FriendService(@Autowired IFriendRepository friendRepository) {
        this.friendRepository=friendRepository;
    }

    @Override
    public void sendInvitation(Friend friend) {
        friendRepository.sendInvitation(friend);
    }

    @Override
    public void acceptInvitation(int friendId) {
        friendRepository.acceptInvitation(friendId);
    }

    @Override
    public void deleteInvitation(int friendId) {
        friendRepository.deleteInvitation(friendId);
    }

    @Override
    public void deleteFriend(int idFriend) {
        friendRepository.deleteFriend(idFriend);
    }

    @Override
    public List<FriendDTO> getListFriend(int userId, int startGetter) {
        return friendRepository.getListFriend(userId,startGetter);
    }

    @Override
    public List<FriendNoConfirmDTO> getListSenderFriend(int userId, int startGetter) {
        return friendRepository.getListSenderFriend(userId,startGetter);
    }

    @Override
    public List<FriendNoConfirmDTO> getListReceiverFriend(int userId, int startGetter) {
        return friendRepository.getListReceiverFriend(userId,startGetter);
    }
}
