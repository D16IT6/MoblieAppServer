package com.mobileapp.repositorys;

import com.mobileapp.DTO.ConversationDTO;
import com.mobileapp.entitys.Conversations;
import com.mobileapp.entitys.User;

import java.util.List;

public interface IConversationRepository {
    public void createConversation(Conversations conversations);
    public void deleteConversation(int idConversation);
    public List<ConversationDTO> getListConversation(int userId, int startGetter);
}

