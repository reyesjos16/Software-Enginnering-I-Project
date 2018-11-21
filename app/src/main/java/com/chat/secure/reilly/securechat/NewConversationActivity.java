package com.chat.secure.reilly.securechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseAuth;


public class NewConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_conversation_activity);

        //the user clicked create new conversation
        Button createConversationButton = (Button)findViewById(R.id.createConversationButton);
        final EditText getOtherUserEmail = (EditText)findViewById(R.id.usernameForNewConversation);


        createConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this will hold the text the user entered in the text box
                String otherUserEmail = getOtherUserEmail.getText().toString();
                Log.v("The email", otherUserEmail);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //!!!!need to check if the user otheruseremail is a registered user

                String deviceUserEmail;

                if(user != null){
                    deviceUserEmail = user.getEmail();
                    Log.v("User email", deviceUserEmail);

                    //prints a dialog to the screen
                    Toast.makeText(getApplicationContext(), "Creating Conversation",
                            Toast.LENGTH_LONG).show();


                    //need to check if conversation alreadt exists

                    //create conversation object
                    Conversation newConversation = new Conversation(deviceUserEmail, otherUserEmail);
                    DatabaseReference newConversationDBRefernce = DatabaseOperations.pushChat(newConversation);

                    String newConvoPath = newConversationDBRefernce.toString();

                    Intent i = new Intent(NewConversationActivity.this, MessageActivity.class);
                    //passes path to db ref as a string
                    i.putExtra("conversation", newConvoPath);

                    startActivity(i);



                    //need pass db ref to message activity



                }else{
                    Toast.makeText(getApplicationContext(), "Not logged in",
                            Toast.LENGTH_LONG).show();
                }


                //need check if use exists then create conversation

                //then open message activity with a reference to the message list for the new conversation
            }
        });


    }

}
