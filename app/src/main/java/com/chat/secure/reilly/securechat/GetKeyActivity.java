package com.chat.secure.reilly.securechat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class GetKeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if key is written to disk
        Bundle extras = getIntent().getExtras();
        String pathToConvo = extras.getString("conversation");


        
        setContentView(R.layout.activity_get_key);

        Button openConvoButton = (Button)findViewById(R.id.openEConversation);
        final EditText getPassswordTextBox = (EditText)findViewById(R.id.password);

        final Switch savePasswordSwitch = (Switch) findViewById(R.id.saveKeySwitch);







    }

}
