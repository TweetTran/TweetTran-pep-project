package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {

        Javalin app = Javalin.create();

        // account action endpoint
        app.post("/login", this::loginPost);
        app.post("/register", this::registrationPost);

        // message action endpoint
        app.get("/messages", this::getAllMessage);
        app.post("/messages", this::postMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/messages/{message_id}", this::getMessageFromID);
        app.delete("/messages/{message_id}", this::removeMessageFromID);
        app.get("/accounts/{account_id}/messages", this::getMessageFromUser);


        return app;
    } 

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

     private AccountService as = new AccountService();
     private MessageService ms = new MessageService();
     ObjectMapper om = new ObjectMapper();


     // registration handlers 
    private void registrationPost(Context ctx) {
        try {
            Account account = om.readValue(ctx.body(), Account.class);
            Account savedAccount = as.accountRegister(account);
            if (savedAccount != null) {
                ctx.status(200);
                ctx.json(savedAccount);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    // login handlers
    private void loginPost(Context ctx) {
        try {
            Account account = om.readValue(ctx.body(), Account.class);
            Account loginAccount = as.login(account.getUsername(), account.getPassword());
            if (loginAccount != null) {
                ctx.status(200);
                ctx.json(loginAccount);
            } else {
                ctx.status(401);
            }
        } catch (Exception e) {
            ctx.status(401);
        }
    }

    // message handlers
    private void getAllMessage(Context ctx) {
        ctx.status(200);
        ctx.json(ms.getAllMessages());
    }

    private void postMessage(Context ctx) {
        try {
            Message msg = om.readValue(ctx.body(), Message.class);
            
            if (msg.getMessage_text() == null || msg.getMessage_text().isBlank() || msg.getMessage_text().length() > 255) {
                ctx.status(400);
                return;
            }   
            Message created = ms.createMessage(msg.getPosted_by(), msg);
            if (created != null) {
                ctx.status(200);
                ctx.json(created);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
    }
}

    private void updateMessage(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            Message body = om.readValue(ctx.body(), Message.class);
            if (body.getMessage_text().isBlank() || body.getMessage_text().length() > 255) {
                ctx.status(400);
                return;
            }
            Message updated = ms.updateSMSByID(id, body.getMessage_text());
            if (updated != null) {
                ctx.status(200);
                ctx.json(updated);
            } else {
                ctx.status(400); 
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }
    private void getMessageFromID(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = ms.searchSMSByID(id);
        if (msg != null) {
            ctx.status(200);
            ctx.json(msg);
        }
        else {
            ctx.status(100);
        }
    }

    private void removeMessageFromID(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = ms.deleteSMSByID(id);
        if (deleted != null) {
            ctx.status(200);          
            ctx.json(deleted);       
        } else {
            ctx.status(200);
            ctx.result("");        
        }
    }

    private void getMessageFromUser(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.status(200);
        ctx.json(ms.getUserMessages(userId));
    }

}