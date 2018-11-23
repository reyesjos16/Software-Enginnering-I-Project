package com.chat.secure.reilly.securechat;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;

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



    /*public static boolean chatExists(String user1, String user2)
    {
        DatabaseReference c = FirebaseDatabase.getInstance().getReference().child(CHATS_CHILD);

        final String key  = Conversation.computeConversationPrimaryKey(user1, user2);
        Log.v("key!!!!", key);

        c.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
               dataSnapshot.hasChild(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        if(!chats.child(key).getKey().equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/

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
