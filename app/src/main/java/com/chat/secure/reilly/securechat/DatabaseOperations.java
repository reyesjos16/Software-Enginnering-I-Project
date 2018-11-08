package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;

public class DatabaseOperations
{
    private static final String CHATS_CHILD = "chats";
    private static DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    public static void pushChat(Conversation conv)
    {
        mFirebaseDatabaseReference.child(CHATS_CHILD).push().setValue(conv);
    }



    public static void testChatAdd()
    {
        Conversation a = new Conversation("userone", "usertwo");
        a.addMessage(new FriendlyMessage("working?", "Jonathan", "photourl", "imageurl"));
        a.addMessage(new FriendlyMessage("yup its working", "Jonathan", "photourl", "imageurl"));
        pushChat(a);
    }
}
