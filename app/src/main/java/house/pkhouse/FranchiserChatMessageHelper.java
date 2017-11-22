package house.pkhouse;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by User-10 on 23-Aug-17.
 */

public class FranchiserChatMessageHelper {

    private String messageText;
    private String messageUser;
   // private long messageTime;
    String messageTime;

    public FranchiserChatMessageHelper(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
       long datatime = new Date().getTime();
        String dattime = Long.toString(datatime);
        this.messageTime = dattime;

    }

    public FranchiserChatMessageHelper(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime(){return messageTime;}
    public void setMessageTime(String datetime){
        this.messageTime = datetime;
    }

   /* public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
    */
}