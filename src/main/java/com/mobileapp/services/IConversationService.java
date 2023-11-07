package com.mobileapp.services;

import com.mobileapp.DTO.ConversationDTO;
import com.mobileapp.DTO.MessageDTO;
import com.mobileapp.entitys.Message;
import com.mobileapp.entitys.User;

import java.util.List;

public interface IConversationService {
    public void createConversation(int userIdFirst, int userIdSecond);
    public void deleteConversation(int idConversation);
    public List<ConversationDTO> getListConversation(int userId, int startGetter);
}
