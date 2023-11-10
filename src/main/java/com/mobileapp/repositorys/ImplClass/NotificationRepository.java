package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.DTO.CommentReceiverDTO;
import com.mobileapp.DTO.NotificationDTO;
import com.mobileapp.repositorys.INotificationRepository;
import com.mobileapp.utils.ConvertData;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotificationRepository implements INotificationRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final ConvertData convertData;
    public NotificationRepository(){
        convertData=new ConvertData();
    }
    @Transactional
    @Override
    public int addNotification(CommentReceiverDTO comment, Timestamp timestampAdd,String type){
        try {
            StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("add_notification_real_time");
            storedProcedureQuery.registerStoredProcedureParameter("user_id_sender",Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("user_id_receiver",Integer.class,ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("post_id",Integer.class,ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("type",String.class,ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("time_notification", Timestamp.class,ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("notification_id",Integer.class,ParameterMode.OUT);

            storedProcedureQuery.setParameter("user_id_sender",comment.getIdUserComment());
            storedProcedureQuery.setParameter("user_id_receiver",comment.getIdReceiverUser());
            storedProcedureQuery.setParameter("post_id",comment.getIdPostComment());
            storedProcedureQuery.setParameter("type",type);
            storedProcedureQuery.setParameter("time_notification",timestampAdd);

            storedProcedureQuery.execute();
            return (int) (Integer) storedProcedureQuery.getOutputParameterValue("notification_id");
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
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
