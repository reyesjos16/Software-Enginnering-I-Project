package com.chat.secure.reilly.securechat;

import com.google.firebase.database.IgnoreExtraProperties;

//conversation without message list so it con be converted to proper object

@IgnoreExtraProperties
public class ConversationLite {
    public String user1;
    public String user2;
    public String primaryKey;
    private boolean isEncrypted; //true if convo is encrypted
    public boolean user1HasLeft; //true when a user has elected to leave conversation
    public boolean user2HasLeft;

    public ConversationLite() {
        this.user1 = "";
        this.user2 = "";
        this.primaryKey = "";
        this.isEncrypted = false;
        this.user1HasLeft = false;
        this.user2HasLeft = false;

    }

    public ConversationLite(String user1, String user2){
        this.user1 = user1;
        this.user2 = user2;
        this.setPrimaryKey(user1, user2);
        this.isEncrypted = false;
        this.user1HasLeft = false;
        this.user2HasLeft = false;
    }

    public ConversationLite(String user1, String user2, boolean convoIsEncrypted){
        this.user1 = user1;
        this.user2 = user2;
        this.setPrimaryKey(user1, user2);
        this.isEncrypted = convoIsEncrypted;
        this.user1HasLeft = false;
        this.user2HasLeft = false;
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



    public void setIsEncrypted(boolean isE){
        this.isEncrypted = isE;
    }

    public void setUser1HasLeft(boolean user1HasLeft) {
        this.user1HasLeft = user1HasLeft;
    }

    public void setUser2HasLeft(boolean user2HasLeft) {
        this.user2HasLeft = user2HasLeft;
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

    public boolean getIsEncrypted(){ return this.isEncrypted; }

    public boolean getUser1HasLeft() {
        return user1HasLeft;
    }

    public boolean getUser2HasLeft() {
        return user2HasLeft;
    }

    public boolean convoIsEncrypted() {
        return this.isEncrypted;
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
        if(!currentUser.equals(this.user1)){
            return this.user1;
        }else{
            return this.user2;
        }
    }

    //assumes user with gmail address username is a member of the conversation
    public boolean hasLeft(String username){
        if(username.equals(this.user1)){
            return user1HasLeft;
        }else{
            return user2HasLeft;
        }
    }

    //returns true if all members have left the conversation
    public boolean convoIsAbandoned(){
        return this.user1HasLeft && this.user2HasLeft;
    }
}

