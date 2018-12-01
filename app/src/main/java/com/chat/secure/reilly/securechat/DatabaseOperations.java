package com.chat.secure.reilly.securechat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import android.widget.Toast;
import android.app.Activity;


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


    //assumes username is a member of conversation
    //deletes conversation if it becomes abandoned
    public static void leaveConversation(DatabaseReference conv, final String username){
        final DatabaseReference r = conv;
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    ConversationLite c = dataSnapshot.getValue(ConversationLite.class);

                    if(username.equals(c.getUser1())){
                        r.child("user1HasLeft").setValue(true);
                        c.setUser1HasLeft(true);
                    }else{
                        r.child("user2HasLeft").setValue(true);
                        c.setUser2HasLeft(true);
                    }

                    if(c.isAbandoned()){
                        r.removeValue();
                    }

                    Log.wtf("Didnt fail", "");

                }else{
                    Log.wtf("no children", "");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
