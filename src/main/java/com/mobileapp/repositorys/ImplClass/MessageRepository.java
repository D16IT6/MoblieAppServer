package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.MessageDTO;
import com.mobileapp.entitys.Message;
import com.mobileapp.repositorys.IMessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MessageRepository implements IMessageRepository {
    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public List<MessageDTO> getListMessage(int userConversation, int startGetter) {
        return null;
    }

    @Override
    public void deleteMessage(int idMessage) {

    }
}
