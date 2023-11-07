package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.ConversationDTO;
import com.mobileapp.DTO.MessageDTO;
import com.mobileapp.entitys.Conversations;
import com.mobileapp.entitys.Message;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.IConversationRepository;
import com.mobileapp.repositorys.IMessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Repository
public class ConversationRepository implements IConversationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createConversation(Conversations conversations) {
        try {
            entityManager.persist(conversations);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteConversation(int idConversation) {

    }

    @Override
    public List<ConversationDTO> getListConversation(int userId, int startGetter) {
        return null;
    }
}
