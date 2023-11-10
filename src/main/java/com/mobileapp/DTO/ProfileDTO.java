package com.mobileapp.DTO;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileDTO {
    private String userName;
    private String fullName;
    private String urlAvatar;
    private String email;
    private int amountFriend;
    private int amountPost;
    private String describe;
}
