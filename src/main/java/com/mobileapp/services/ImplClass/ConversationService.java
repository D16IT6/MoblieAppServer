package com.mobileapp.services.ImplClass;

import com.mobileapp.DTO.ConversationDTO;
import com.mobileapp.DTO.MessageDTO;
import com.mobileapp.entitys.Conversations;
import com.mobileapp.entitys.Message;
import com.mobileapp.entitys.Participants;
import com.mobileapp.entitys.User;
import com.mobileapp.repositorys.IConversationRepository;
import com.mobileapp.repositorys.IParticipantRepository;
import com.mobileapp.services.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
public class ConversationService implements IConversationService {
    private final IConversationRepository conversationRepository;
    private final IParticipantRepository participantRepository;

    public ConversationService(@Autowired IConversationRepository conversationRepository,@Autowired IParticipantRepository participantRepository) {
        this.conversationRepository = conversationRepository;
        this.participantRepository=participantRepository;
    }

    @Override
    public void createConversation(int userIdFirst, int userIdSecond) {
        Timestamp timestamp=new Timestamp(new Date().getTime());
        StringBuilder idConversation=new StringBuilder();
        idConversation.append(idConversation);
        idConversation.append(userIdFirst);
        idConversation.append(userIdSecond);
        Conversations conversations=new Conversations();
        conversations.setConversationsId(new String(idConversation));

        User userCreate=new User();
        userCreate.setUserId(userIdFirst);
        User userSecond=new User();
        userSecond.setUserId(userIdSecond);
        conversations.setCreatorUser(userCreate);

        conversationRepository.createConversation(conversations);

        Participants participants=new Participants();
        participants.setHide(false);
        participants.setUserParticipants(userCreate);
        participants.setConversationsParticipants(conversations);
        participantRepository.addParticipant(participants);

        Participants participantsSecond=new Participants();
        participantsSecond.setHide(false);
        participantsSecond.setUserParticipants(userSecond);
        participantsSecond.setConversationsParticipants(conversations);
        participantRepository.addParticipant(participantsSecond);
    }

    @Override
    public void deleteConversation(int idConversation) {

    }

    @Override
    public List<ConversationDTO> getListConversation(int userId, int startGetter) {
        return null;
    }

}
