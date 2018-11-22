package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class DatabaseOperations
{
    private static final String CHATS_CHILD = "chats";

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    private static final DatabaseReference chats = root.child(CHATS_CHILD);

    //Posts conv to firebase under chats child
    //Returns a reference to the new chat
    public static DatabaseReference pushChat(Conversation conv)
    {
        //This line is not needed or used
        //https://stackoverflow.com/questions/43029700/does-initializing-firebasedatabase-getinstance-multiple-times-affect-performan
        DatabaseReference c = FirebaseDatabase.getInstance().getReference().child(CHATS_CHILD);
        DatabaseReference newchat = chats.child(conv.getPrimaryKey());
        newchat.setValue(conv);

        return newchat;
    }


    //Add message to conversation
    public static void addMessageToConv(FriendlyMessage message, DatabaseReference conv){

        conv.child("messageList").push().setValue(message);

    }

    public static boolean chatExists(String user1, String user2)
    {
        if(chats.child(user1 + "-" + user2).getKey() != "")
        {
            return true;
        }
        else
        {
            if(chats.child(user2 + "-" + user1).getKey() != "")
            {
                return true;
            }
        }
        return false;
    }

    public static void testChatAdd()
    {
        Conversation a = new Conversation("userone1", "usertwo2");
        a.addMessage(new FriendlyMessage("working?", "Jonathan", "photourl", "imageurl"));
        a.addMessage(new FriendlyMessage("borked", "Jonathan", "photourl", "imageurl"));
       // pushChat(a);
        DatabaseReference firebaseR = pushChat(a);
        addMessageToConv(new FriendlyMessage("Sophia is it working?", "Jonathan", "photourl", "imageurl"), firebaseR);
    }

    public static void testMessageAdd(){
        Conversation a = new Conversation("user117", "user118");
        //a.addMessage(new FriendlyMessage("working?", "Jonathan", "photourl", "imageurl"));
        //a.addMessage(new FriendlyMessage("borked", "Jonathan", "photourl", "imageurl"));


        DatabaseReference firebaseR = pushChat(a);
        addMessageToConv(new FriendlyMessage("Sophia is it working?", "Jonathan", "photourl", "imageurl"), firebaseR);

        FriendlyMessage m = new FriendlyMessage("borked", "Jonathan", "photourl", "imageurl");
        addMessageToConv(m, firebaseR);
    }
}
