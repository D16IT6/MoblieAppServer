package com.mobileapp.controllers;

import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.services.ImplClass.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(@Autowired NotificationService notificationService){
        this.notificationService=notificationService;
    }
    @GetMapping("/get-list-notification")
    public List<NotificationDTO> getListNotification(@RequestParam(name = "userId",defaultValue = "1")int userId,
                                                     @RequestParam(name = "startGetter",defaultValue = "0")int startGetter){
        startGetter*=15;
        return notificationService.getListNotification(userId,startGetter);
    }
}
