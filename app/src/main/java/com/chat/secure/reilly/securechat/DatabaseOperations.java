package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseOperations
{
    private static final String CHATS_CHILD = "chats";

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    private static final DatabaseReference chats = root.child(CHATS_CHILD);

    //Posts conv to firebase under chats child
    //Returns a reference to the new chat
    public static DatabaseReference pushChat(Conversation conv)
    {
        DatabaseReference newchat = chats.child(conv.getPrimaryKey());
        newchat.setValue(conv);

        return newchat;
    }


    //Add message to conversation
    public static void addMessageToConv(Message message, DatabaseReference conv){

        conv.child("messageList").push().setValue(message);

    }

    public static void testChatAdd()
    {
        Conversation a = new Conversation("userone1", "usertwo2");
        a.addMessage(new Message("working?", "Jonathan", "photourl", "imageurl"));
        a.addMessage(new Message("borked", "Jonathan", "photourl", "imageurl"));
       // pushChat(a);
        DatabaseReference firebaseR = pushChat(a);
        addMessageToConv(new Message("Sophia is it working?", "Jonathan", "photourl", "imageurl"), firebaseR);
    }

    public static void testMessageAdd(){
        Conversation a = new Conversation("user117", "user118");
        //a.addMessage(new Message("working?", "Jonathan", "photourl", "imageurl"));
        //a.addMessage(new Message("borked", "Jonathan", "photourl", "imageurl"));


        DatabaseReference firebaseR = pushChat(a);
        addMessageToConv(new Message("Sophia is it working?", "Jonathan", "photourl", "imageurl"), firebaseR);

        Message m = new Message("borked", "Jonathan", "photourl", "imageurl");
        addMessageToConv(m, firebaseR);
    }
}
