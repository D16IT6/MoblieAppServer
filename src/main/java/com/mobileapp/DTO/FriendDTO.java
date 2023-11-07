package com.mobileapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {
    private int friendInvitationId;
    private int userId;
    private String fullName;
    private String urlAvatar;
    private int amountMutualFriend;
}
