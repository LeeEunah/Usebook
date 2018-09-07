package com.example.eunah.eosproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eunah.eosproject.ChatActivity;
import com.example.eunah.eosproject.R;
import com.example.eunah.eosproject.data.UserMessageData;

import java.util.ArrayList;

/**
 * Created by leeeunah on 2018. 3. 3..
 */

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.MessageViewHolder> {
    private Context context;
    private ArrayList<UserMessageData> userMessageList = new ArrayList<>();
    private static final String TAG = "Error";

    public UserMessageAdapter(Context context, ArrayList<UserMessageData> userMessageList){
        this.context = context;
        this.userMessageList = userMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_list_item, parent, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final UserMessageData userMessageData = userMessageList.get(position);
        ((MessageViewHolder)holder).contentTxt.setText(userMessageData.getMessage());
        ((MessageViewHolder)holder).dateTxt.setText(userMessageData.getDate());
//        Log.e(TAG, "user: "+userMessageData.getUserId());
//        Log.e(TAG, "desti: "+userMessageData.getDestinationUserId());
//        Log.e(TAG, "my: "+userMessageData.getMyId());


        if (userMessageData.getDestinationUserId().equals(userMessageData.getMyId())){
            ((MessageViewHolder)holder).destinationUserIdTxt.setText(userMessageData.getUserId());
        } else{
            ((MessageViewHolder)holder).destinationUserIdTxt.setText(userMessageData.getDestinationUserId());
        }
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView destinationUserIdTxt, contentTxt, dateTxt;

        public MessageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            destinationUserIdTxt = (TextView) itemView.findViewById(R.id.destination_user_id_txt);
            contentTxt = (TextView) itemView.findViewById(R.id.content_txt);
            dateTxt = (TextView) itemView.findViewById(R.id.date_txt2);

        }

        @Override
        public void onClick(View view) {
            final UserMessageData userMessageData = userMessageList.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), ChatActivity.class);
//            Log.e(TAG, "user: "+userMessageList.get(getAdapterPosition()).getDestinationUserId());
            if (userMessageData.getDestinationUserId().equals(userMessageData.getMyId())) {
                intent.putExtra("destinationUserId", userMessageData.getUserId());
                intent.putExtra("sender", userMessageData.getUserId());
            } else {
                intent.putExtra("destinationUserId", userMessageData.getDestinationUserId());
                intent.putExtra("sender", userMessageData.getUserId());
            }
            view.getContext().startActivity(intent);
        }
    }
}
