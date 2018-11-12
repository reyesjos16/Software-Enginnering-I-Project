package com.chat.secure.reilly.securechat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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

                //need check if use exists then create conversation

                //then open message activity with a reference to the message list for the new conversation
            }
        });


    }

}
