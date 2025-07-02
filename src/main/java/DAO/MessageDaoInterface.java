package DAO;


import java.util.List;
import Model.Message;

/**
 * MessageDaoInterface
 */
public interface MessageDaoInterface {

    List<Message> getAllMessages();
    List<Message> getUserMessages(int posted_by);
    Message searchSMSByID(int message_id);
    Message deleteSMSByID(int message_id);
    Message updateSMSByID(int message_id,String message_updated);
    Message createMessage(Message message);
    
} 