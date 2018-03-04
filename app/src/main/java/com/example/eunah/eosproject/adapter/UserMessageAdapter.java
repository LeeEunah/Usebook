package com.example.eunah.eosproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView userIdTxt, contentTxt, dateTxt;

        public MessageViewHolder(View itemView) {
            super(itemView);

            userIdTxt = (TextView) itemView.findViewById(R.id.user_id_txt);
            contentTxt = (TextView) itemView.findViewById(R.id.content_txt);
            dateTxt = (TextView) itemView.findViewById(R.id.date_txt2);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ChatActivity.class);
//            intent.putExtra("destinationUserId", )
//            intent.putExtra("position", getAdapt/erPosition());
            view.getContext().startActivity(intent);
        }
    }
}
