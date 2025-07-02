package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {

    private MessageDAO messageDAO = new MessageDAO();


    // retrieve all messages 
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message>  getUserMessages(int posted_by){
        return messageDAO. getUserMessages(posted_by);
    }
    
    // retrieve message by it id
    public Message searchSMSByID(int message_id){
        return messageDAO.searchSMSByID(message_id);
    }

    public Message deleteSMSByID(int message_id){
        return messageDAO.deleteSMSByID(message_id);
    }

    // update by ID 
    public Message updateSMSByID(int message_id, String message_updated){
        return messageDAO.updateSMSByID(message_id, message_updated);
    }
    
    // create a new message
    public Message createMessage(int accountId, Message message){
        message.setPosted_by(accountId);
        return messageDAO.createMessage(message);
    }
}
