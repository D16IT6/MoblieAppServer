package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.entitys.Notification;
import com.mobileapp.repositorys.INotificationRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotificationRepository implements INotificationRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private ConvertData convertData;
    public NotificationRepository(){
        convertData=new ConvertData();
    }
    @Transactional
    @Override
    public void addNotification(Notification notification){
        entityManager.persist(notification);
    }
    @Transactional
    @Override
    public List<NotificationDTO> getListNotification(int userId, int startGetter){
        Query query=entityManager.createQuery(
                "select n.notificationId,n.postNotification.postId,n.userSender.fullName," +
                        "n.userSender.urlAvata,n.isRead,n.type,n.timeNotification " +
                "from Notification as n " +
                "where n.userReceiver.userId=:userId " +
                "order by n.notificationId DESC");
        query.setParameter("userId",userId);
        query.setFirstResult(startGetter);
        query.setMaxResults(15);
        List<NotificationDTO>notificationDTOList=new ArrayList<>();
        try {
            List<Object[]> list=query.getResultList();
            notificationDTOList=list.stream().map(convertData::convertNotificationDTO).collect(Collectors.toList());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return notificationDTOList;
    }
}
