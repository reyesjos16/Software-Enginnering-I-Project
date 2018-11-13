package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;

public class DatabaseOperations
{
    private static final String CHATS_CHILD = "chats";

    //Static reference to firebase database instance
    private static DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    //Static reference to chats child in firebase
    private static DatabaseReference chats = mFirebaseDatabaseReference.child(CHATS_CHILD);

    //Posts conv to firebase under chats child
    //Returns a reference to the new chat
    public static DatabaseReference pushChat(Conversation conv)
    {
        DatabaseReference newchat = chats.push();
        newchat.setValue(conv);
        return newchat;
    }

    //Test function created by Jonathan to test pushChat
    public static void testChatAdd()
    {
        Conversation a = new Conversation("userone1", "usertwo2");
        a.addMessage(new FriendlyMessage("working?", "Jonathan", "photourl", "imageurl"));
        a.addMessage(new FriendlyMessage("yup its working", "Jonathan", "photourl", "imageurl"));
        pushChat(a);
    }
}
