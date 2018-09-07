package com.example.eunah.eosproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eunah.eosproject.data.ChatData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "Error";
    private static int check = 0;
    private String destinationUserId, userId, sender, chatRoomUid;
    private TextView userIdTxt;
    private EditText chatSendEdit;
    private Button chatSendBtn;
    private RecyclerView chatRecyclerView;
    private ArrayList<ChatData.Comment> commentsList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userIdTxt = findViewById(R.id.user_id_txt2);
        chatSendEdit = findViewById(R.id.chat_send_edit);
        chatSendBtn = findViewById(R.id.chat_send_btn);
        chatRecyclerView = findViewById(R.id.chat_recyclerview);
        destinationUserId = getIntent().getStringExtra("destinationUserId");
        sender = getIntent().getStringExtra("sender");
        Log.e(TAG, "sender: "+sender);
        userIdTxt.setText(destinationUserId);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        userId = user.getEmail().substring(0, index);

        if (sender == null)
            sender = userId;

        checkChatRoom();
    }

    public void setComment(){
        ChatData.Comment comment = new ChatData.Comment();
        comment.userId = userId;
        comment.message = chatSendEdit.getText().toString();
        Date date = new Date();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        comment.date = currentDate.format(date).toString();

        Log.e(TAG,"message: "+comment.message);
        if (check == 1){
            if (comment.message.getBytes().length <= 0)
                Toast.makeText(this, getResources().getString(R.string.empty_editText), Toast.LENGTH_LONG).show();
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment);
            chatSendEdit.setText(null);
        }
    }

    public void checkChatRoom(){
        firebaseDatabase.getReference().child("chatrooms").orderByChild("users/user").equalTo(sender)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            ChatData chatData = item.getValue(ChatData.class);
                            if(chatData.users.containsValue(destinationUserId)){
                                chatRoomUid = item.getKey();
                                Log.e(TAG, "check: "+check);
                                if (check == 0) {
                                    setComment();
                                    check = 1;
                                }

                                chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                                refreshData();
                                chatSendBtn.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void refreshData() {
        if (chatAdapter == null) {
//            Log.e(TAG, "refresh null");
            chatRecyclerView.setAdapter(new ChatAdapter());
        } else{
//            Log.e(TAG, "refresh else");
            chatRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_send_btn:

                ChatData chatData = new ChatData();
                chatData.users.put("user", userId);
                chatData.users.put("destination", destinationUserId);

                if(chatRoomUid == null) {
                    chatSendBtn.setEnabled(false);
                    Log.e(TAG, "onClick: null");
                    firebaseDatabase.getReference().child("chatrooms").push().setValue(chatData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });

                }else{
                    Log.e(TAG, "onClick: else " );
                    setComment();
                }
                break;

            case R.id.back_btn:
                finish();
                break;
        }
    }

    class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        public ChatAdapter() {
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            commentsList.clear();

                            for(DataSnapshot item : dataSnapshot.getChildren()){
                                commentsList.add(item.getValue(ChatData.Comment.class));
                            }
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
            return new OthersChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.e(TAG, "ChatUser: "+commentsList.get(position).userId);


            ((OthersChatViewHolder)holder).chatTxt.setText(commentsList.get(position).message) ;
            ((OthersChatViewHolder)holder).userIdTxt.setText(commentsList.get(position).userId);
            ((OthersChatViewHolder)holder).dateTxt.setText(commentsList.get(position).date);


        }

        @Override
        public int getItemCount() {
            return commentsList.size() ;
        }
    }

    private class OthersChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatTxt, userIdTxt, dateTxt;

        public OthersChatViewHolder(View view) {
            super(view);
            chatTxt = view.findViewById(R.id.chat_txt);
            userIdTxt= view.findViewById(R.id.user_id_txt3);
            dateTxt = view.findViewById(R.id.date_txt3);
        }
    }
}
