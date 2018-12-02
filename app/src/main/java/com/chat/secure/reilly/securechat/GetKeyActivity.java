package com.chat.secure.reilly.securechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.util.Log;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetKeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if key is written to disk
        Bundle extras = getIntent().getExtras();
        final String pathToConvo = extras.getString("conversation");


        DatabaseReference convo = FirebaseDatabase.getInstance().getReferenceFromUrl(pathToConvo);
        final String convoPrimaryKey = convo.getKey();

        String savedKey = LocalKey.readKey(convoPrimaryKey,GetKeyActivity.this);

        if(!savedKey.equals("")){
            Intent i = new Intent(GetKeyActivity.this, MessageActivity.class);
            //passes path to db ref as a string
            i.putExtra("conversation", pathToConvo);
            i.putExtra("password", savedKey);
            startActivity(i);
            finish();

        }
        
        setContentView(R.layout.activity_get_key);

        Button openConvoButton = (Button)findViewById(R.id.openEConversation);
        final EditText getPassswordTextBox = (EditText)findViewById(R.id.password);

        final Switch savePasswordSwitch = (Switch) findViewById(R.id.saveKeySwitch);

        openConvoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = getPassswordTextBox.getText().toString();

                if(password.equals("")){
                    Toast.makeText(getApplicationContext(), "Password Cannot be Empty",
                            Toast.LENGTH_LONG).show();
                }else{
                    if(savePasswordSwitch.isChecked()){
                        LocalKey.writeKey(convoPrimaryKey, password, GetKeyActivity.this);
                    }

                    Intent i = new Intent(GetKeyActivity.this, MessageActivity.class);
                    i.putExtra("conversation", pathToConvo);
                    i.putExtra("password", password);
                    startActivity(i);
                }
            }
        });





    }


}
