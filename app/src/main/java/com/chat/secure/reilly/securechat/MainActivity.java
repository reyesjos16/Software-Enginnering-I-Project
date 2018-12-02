package com.chat.secure.reilly.securechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {



    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";

    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private GoogleApiClient mGoogleApiClient;

    DatabaseReference chatRef;
    ChildEventListener convoListener;


    final List<ConversationLite> convoList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize Firebase Measurement.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        // Fetch remote config.
        fetchConfig();

        Button openNewConversationActivity = (Button)findViewById(R.id.newConversationButton);

        openNewConversationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewConversationActivity.class));
            }
        });

        //recycler view
        RecyclerView  rvConversations = (RecyclerView)findViewById(R.id.conversationRecyclerView);

        final ConversationAdapter adapter = new ConversationAdapter(convoList);
        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));


        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));

        final String currentUserEmail = mFirebaseUser.getEmail();

        //TODO have single value event listner run once to initalize chatList


        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");
        /*chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ConversationLite cL = dataSnapshot.getValue(ConversationLite.class);


                    if(cL.isMember(currentUserEmail)){
                        boolean wasIn = false;
                        for(int i = 0; i < convoList.size(); i++){
                            ConversationLite curr = convoList.get(i);
                            if(curr.getPrimaryKey().equals(cL.getPrimaryKey())){
                                wasIn = true;
                            }
                        }
                        if(wasIn == false && cL.hasLeft(currentUserEmail) == false){
                            convoList.add(0, cL);
                            adapter.notifyItemInserted(0);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        convoListener = chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    ConversationLite cL = dataSnapshot.getValue(ConversationLite.class);

                    if(cL.isMember(currentUserEmail)){
                        boolean wasIn = false;
                        for(int i = 0; i < convoList.size(); i++){
                            ConversationLite curr = convoList.get(i);
                            if(curr.getPrimaryKey().equals(cL.getPrimaryKey())){
                                wasIn = true;
                            }
                        }
                        if(wasIn == false && cL.hasLeft(currentUserEmail) == false){
                            convoList.add(0, cL);
                            adapter.notifyItemInserted(0);
                        }

                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ConversationLite cL = dataSnapshot.getValue(ConversationLite.class);
                String pk = String.valueOf(convoList.size());

                if(cL.isMember(currentUserEmail)){
                    if(cL.hasLeft(currentUserEmail)){
                        for(int i = 0; i < convoList.size(); i++){
                            ConversationLite curr = convoList.get(i);
                            if(curr.getPrimaryKey().equals(cL.getPrimaryKey())){

                                //remove key if saved
                                if(!LocalKey.readKey(curr.getPrimaryKey(), MainActivity.this).equals("") ) {
                                    LocalKey.removeKey(curr.getPrimaryKey(), MainActivity.this);
                                }

                                //update recycler view
                                convoList.remove(i);
                                adapter.notifyItemRemoved(i);
                                adapter.notifyItemRangeChanged(i, convoList.size());
                            }

                        }
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);
        MenuItem leaveChat = (MenuItem) menu.findItem(R.id.leave_conversation);
        leaveChat.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mUsername = ANONYMOUS;
                mPhotoUrl = null;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Fetch the config to determine the allowed length of messages.
    public void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via FirebaseRemoteConfig get<type> calls.
                        mFirebaseRemoteConfig.activateFetched();
                        //applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // There has been an error fetching the config
                        Log.w(TAG, "Error fetching config", e);
                        //applyRetrievedLengthLimit();
                    }
                });
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

}


