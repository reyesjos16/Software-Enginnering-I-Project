package com.chat.secure.reilly.securechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = user.getEmail();


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView otherUserTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            otherUserTextView = (TextView) itemView.findViewById(R.id.otherUser);
        }
    }

    private List<Conversation>  convos;

    public ConversationAdapter(List<Conversation> c){
        convos = c;


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
    }

    @Override
    public int getItemCount() {
        return convos.size();
    }
}
