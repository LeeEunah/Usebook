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

import com.example.eunah.eosproject.data.ChatData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "Error";
    private String destinationUserId, userId, chatRoomUid;
    private TextView userIdTxt;
    private EditText chatSendEdit;
    private Button chatSendBtn;
    private RecyclerView chatRecyclerView;
    private ArrayList<ChatData.Comment> commentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userIdTxt = findViewById(R.id.user_id_txt2);
        chatSendEdit = findViewById(R.id.chat_send_edit);
        chatSendBtn = findViewById(R.id.chat_send_btn);
        chatRecyclerView = findViewById(R.id.chat_recyclerview);

        destinationUserId = getIntent().getStringExtra("destinationUserId");
        userIdTxt.setText(destinationUserId);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        userId = user.getEmail().substring(0, index);
        Log.e(TAG, "onClick: chatRoomUid:"+chatRoomUid);

        Log.e(TAG, "onCreate: comment: "+commentsList );
    }

    public void setComment(){
        ChatData.Comment comment = new ChatData.Comment();
        comment.userId = userId;
        comment.message = chatSendEdit.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment);
        chatSendEdit.setText(null);
    }

    public void checkChatRoom(){
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/user").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            ChatData chatData = item.getValue(ChatData.class);
                            if(chatData.users.containsValue(destinationUserId)){
                                chatRoomUid = item.getKey();
                                setComment();

                                chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                                chatRecyclerView.setAdapter(new ChatAdapter());
                                chatSendBtn.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });

//                    checkChatRoom();

                }else{
                    Log.e(TAG, "onClick: else " );
                    setComment();
                }
                break;
        }
    }

    class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        public ChatAdapter() {
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments")
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
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ChatViewHolder)holder).chatTxt.setText(commentsList.get(position).message) ;
        }

        @Override
        public int getItemCount() {
            return commentsList.size() ;
        }
    }

    private class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatTxt;

        public ChatViewHolder(View view) {
            super(view);
            chatTxt = view.findViewById(R.id.chat_txt);
        }
    }
}
