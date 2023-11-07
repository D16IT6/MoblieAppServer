package com.mobileapp.controllers;

import com.mobileapp.DTO.FriendDTO;
import com.mobileapp.DTO.FriendNoConfirmDTO;
import com.mobileapp.services.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {
    private IFriendService friendService;

    public FriendController(@Autowired IFriendService friendService) {
        this.friendService = friendService;
    }
    @GetMapping("/get-list-friend")
    public List<FriendDTO> getListFriend(@RequestParam(name = "userId",defaultValue= "3")int userId,
                                      @RequestParam(name = "startGetter",defaultValue = "0")int startGetter){
        return friendService.getListFriend(userId,startGetter);
    }
    @GetMapping("/get-list-receiver-friend")
    public List<FriendNoConfirmDTO> getListReceiverFriend(@RequestParam(name = "userId",defaultValue = "3")int userId,
                                                          @RequestParam(name = "startGetter",defaultValue = "0")int startGetter){
        return friendService.getListReceiverFriend(userId,startGetter);
    }
    @GetMapping("/get-list-sender-friend")
    public List<FriendNoConfirmDTO> getListSenderFriend(@RequestParam(name = "userId",defaultValue = "1")int userId,
                                                        @RequestParam(name = "startGetter",defaultValue = "0")int startGetter){
        return friendService.getListSenderFriend(userId,startGetter);
    }
}
