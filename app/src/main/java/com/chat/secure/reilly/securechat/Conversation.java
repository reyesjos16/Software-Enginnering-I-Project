package com.chat.secure.reilly.securechat;

import android.support.annotation.NonNull;
import com.chat.secure.reilly.securechat.FriendlyMessage;
import java.util.*;

public class Conversation {

    private String user1;
    private String user2;

    List<FriendlyMessage> messageList = new LinkedList<>();

    public Conversation() {
    }

    public Conversation(String user1, String user2){
        this.user1 = user1;
        this.user2 = user2;
    }



    public List<FriendlyMessage> getMessageList(){
        return this.messageList;
    }

    public void addMessage(FriendlyMessage message){
        messageList.add(message);
    }

    public boolean isMember(String username) {
        if (username.equals(user1)) {
            return true;
        } else if (username.equals(user2)) {
            return true;
        } else
            return false;
    }

    public void setUser1(String user) {
        user1 = user;
    }

    public void setUser2(String user) {
        user2 = user;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }
}

