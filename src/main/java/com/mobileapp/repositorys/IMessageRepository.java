package com.mobileapp.repositorys;

import com.mobileapp.DTO.ConversationDTO;
import com.mobileapp.DTO.MessageDTO;
import com.mobileapp.entitys.Message;
import com.mobileapp.entitys.User;

import java.util.List;

public interface IMessageRepository {
    public void sendMessage(Message message);
    public List<MessageDTO> getListMessage(int userConversation,int startGetter);
    public void deleteMessage(int idMessage);

}
