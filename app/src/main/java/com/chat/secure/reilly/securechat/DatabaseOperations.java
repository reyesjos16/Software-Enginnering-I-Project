package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference c = FirebaseDatabase.getInstance().getReference().child(CHATS_CHILD);

        DatabaseReference newchat = chats.child(conv.getPrimaryKey());
        newchat.setValue(conv);

        return newchat;
    }


    //Add message to conversation
    public static void addMessageToConv(FriendlyMessage message, DatabaseReference conv){

        conv.child("messageList").push().setValue(message);

    }



    //Returns a new reference to chats in firebase
    //private static DatabaseReference getChatRef()
    //{
    //
    //}


/*
    public static void findChat(String user1, String user2)
    {
        Query chatquery = chats.startAt("userone", "user1").endAt("usertwo", "user2");
        Log.v("afksaopwlx", "---------------------------------");
        chatquery.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for(DataSnapshot post : snapshot.getChildren())
                {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError){}
        });
        System.out.println(chatquery);
    }*/

    //public static void main(String[] args)
    //{
    //    findChat("userone", "usertwo");
    //}

    //Test function created by Jonathan to test pushChat
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
