package com.chat.secure.reilly.securechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Log;
import android.widget.Toast;

import java.util.List;



public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = user.getEmail();


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        public TextView otherUserTextView;
        //public Button openButton;

        private final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            otherUserTextView = (TextView) itemView.findViewById(R.id.otherUser);
            //openButton = (Button) itemView.findViewById((R.id.openConvoRecycler));

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();

            if(pos != RecyclerView.NO_POSITION){
                Conversation c = convos.get(pos);
                //Log.v("pos is:", c.getOtherUser(user.getEmail()));
                DatabaseReference dbConvo = FirebaseDatabase.getInstance().getReference().child("chats").child(c.getPrimaryKey());

                if(c.isEncrypted()){
                    Intent i = new Intent(context, GetKeyActivity.class);
                    //passes path to db ref as a string
                    i.putExtra("conversation", dbConvo.toString());

                    context.startActivity(i);

                }else{
                    Intent i = new Intent(context, MessageActivity.class);
                    //passes path to db ref as a string
                    i.putExtra("conversation", dbConvo.toString());

                    context.startActivity(i);

                }

                           }
        }
    }

    private List<Conversation>  convos;

    public ConversationAdapter(List<Conversation> c){
        convos = c;


    }

    public void updateList(List<Conversation> newConvoList){
        this.convos = newConvoList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_conversation, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationAdapter.ViewHolder holder, int position) {
        Conversation c = convos.get(position);

        String otherUser = c.getOtherUser(this.currentUser);

        TextView otherUserTextView = holder.otherUserTextView;
        otherUserTextView.setText(otherUser);

        //Button openButton = holder.openButton;

    }

    @Override
    public int getItemCount() {
        return convos.size();
    }
}
