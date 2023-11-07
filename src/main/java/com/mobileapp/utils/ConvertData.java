package com.mobileapp.utils;

import com.mobileapp.DTO.CommentDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.DTO.PostDTO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Base64;

@Component
public class ConvertData {
    public String convertName(String name){
        return Base64.getEncoder().encodeToString(name.getBytes());
    }
    public PostDTO convertToPostDTO(Object[] objects){
        PostDTO postDTO = new PostDTO();
        postDTO.setIdPost((Integer) objects[0]);
        postDTO.setContent(objects[1].toString());
        postDTO.setUrlImagePost(objects[2].toString());
        postDTO.setTimePost((Timestamp) objects[3]);
        postDTO.setPostLike((int) objects[4]);
        postDTO.setUserIdCreatePost((int) objects[5]);
        postDTO.setFullNameCreatePost((String) objects[6]);
        postDTO.setUrlAvataCreatePost((String) objects[7]);
        postDTO.setAmountComment((int) objects[8]);
        postDTO.setAmountLike((int) objects[9]);
        postDTO.setIsLikePost((Integer) objects[10]);
        return postDTO;
    }
    public CommentDTO convertCommentDTO(Object[] object){
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setIdComment((int) object[0]);
        commentDTO.setIdParentComment((Integer) object[1]);
        commentDTO.setContentComment((String) object[2]);
        commentDTO.setTimeComment((Timestamp) object[3]);
        commentDTO.setIdUserComment((Integer) object[4]);
        commentDTO.setUserNameComment((String) object[5]);
        commentDTO.setUserUrlAvatar((String)object[6]);
        commentDTO.setAmountReply((int)object[7]);
        return commentDTO;
    }
    public NotificationDTO convertNotificationDTO(Object[] objects){
        NotificationDTO notificationDTO=new NotificationDTO();
        notificationDTO.setNotificationId((String) objects[0]);
        notificationDTO.setPostId((Integer) objects[1]);
        notificationDTO.setFullName((String) objects[2]+" ");
        notificationDTO.setUrlAvatarUser((String)objects[3]);
        notificationDTO.setRead((Boolean) objects[4]);
        notificationDTO.setType((String)objects[5]);
        notificationDTO.setTimeNotification((Timestamp) objects[6]);
        return notificationDTO;
    }
    public FriendNoConfirmDTO convertFriendNoConfirm(Object[] objects){
        FriendNoConfirmDTO friendNoConfirmDTO =new FriendNoConfirmDTO();
        friendNoConfirmDTO.setFriendInvitationId((Integer) objects[0]);
        friendNoConfirmDTO.setUserId((Integer) objects[1]);
        friendNoConfirmDTO.setTimeSender((Timestamp) objects[2]);
        friendNoConfirmDTO.setFullName((String) objects[3]);
        friendNoConfirmDTO.setUrlAvatar((String) objects[4]);
        friendNoConfirmDTO.setIsFriend(false);
        return friendNoConfirmDTO;
    }
}
