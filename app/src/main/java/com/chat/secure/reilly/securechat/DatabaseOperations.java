package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;

public class DatabaseOperations
{
    private static final String CHATS_CHILD = "chats";

    //Static reference to firebase database instance
    private static DatabaseReference mFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();

    //Static reference to chats child in firebase


    //Posts conv to firebase under chats child
    //Returns a reference to the new chat
    public static DatabaseReference pushChat(Conversation conv)
    {
        DatabaseReference chats = mFirebaseDatabaseReference.child(CHATS_CHILD);
        DatabaseReference newchat = chats.push();
        newchat.setValue(conv);
        return newchat;
    }
    //Add message to conversation
    public static void addMessageToConv(FriendlyMessage message, DatabaseReference conv){

        //check is message attribute in json

        //add message to conversation
        conv.setValue(message);
    }
    //Test function created by Jonathan to test pushChat
    public static void testChatAdd()
    {
        Conversation a = new Conversation("userone1", "usertwo2");
        a.addMessage(new FriendlyMessage("working?", "Jonathan", "photourl", "imageurl"));
        a.addMessage(new FriendlyMessage("yup its working", "Jonathan", "photourl", "imageurl"));
        DatabaseReference firebaseR = pushChat(a);
        addMessageToConv(new FriendlyMessage("Sophia is it working?", "Jonathan", "photourl", "imageurl"), firebaseR);

    }
}
