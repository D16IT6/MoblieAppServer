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
public class FriendNoConfirmDTO extends FriendDTO{
    private Timestamp timeSender;
    private Boolean isFriend;
}
