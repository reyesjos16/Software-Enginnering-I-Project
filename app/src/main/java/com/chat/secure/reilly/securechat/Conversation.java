package com.chat.secure.reilly.securechat;

import java.util.*;

public class Conversation {

    private String user1;
    private String user2;
    private String primaryKey;

    List<FriendlyMessage> messageList = new LinkedList<>();

    public Conversation()
    {}

    public Conversation(String user1, String user2){
        this.user1 = user1;
        this.user2 = user2;
        this.setPrimaryKey(user1, user2);
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
        }
        else if (username.equals(user2)) {
            return true;
        }
        else
            return false;
    }

    public void setUser1(String user) {
        user1 = user;
    }

    public void setUser2(String user) {
        user2 = user;
    }

    public void setPrimaryKey(String user1, String user2)
    {
        this.primaryKey = computeConversationPrimaryKey(user1, user2);
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public String getPrimaryKey()
    {
        return primaryKey;
    }


    public static String computeConversationPrimaryKey(String user1, String user2){
        String u1 = user1.replaceAll("@gmail.com$","");
        String u2 = user2.replaceAll("@gmail.com$","");

        String pk;

        if(u1.compareTo(u2) < 0){
            pk = u1 + '-' + u2;
        }else{
            pk = u2 + '-' + u1;
        }

        return pk;
    }

    //returns user not equal to currentUser
    //assumes currentUser is a member of the conversation
    public String getOtherUser(String currentUser){
        String otherUser;

        if(!currentUser.equals(this.user1)){
            return this.user1;
        }else{
            return this.user2;
        }
    }
}

