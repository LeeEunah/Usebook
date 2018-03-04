package com.example.eunah.eosproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.eunah.eosproject.adapter.StoreAdapter;
import com.example.eunah.eosproject.adapter.UserMessageAdapter;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.ChatData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.UserMessageData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserMessageActivity extends AppCompatActivity {
    private static final String TAG = "Error";
    private String userId, destinationUserId, recentMessage;
    private RecyclerView userMessageRecyclerView;
    private UserMessageAdapter userMessageAdapter;

    private FirebaseAuth firebaseAuth;

    private ArrayList<UserMessageData> userMessageList = DummyData.userMessageList;
    private ArrayList<ChatData.Comment> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        Log.e(TAG, "onCreate: UserMessageActivity" );

        userMessageRecyclerView = findViewById(R.id.user_message_recyclerview);

        userMessageRecyclerView.setHasFixedSize(true);
        userMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        userId = user.getEmail().substring(0, index);

        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("chatrooms")){
                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        destinationUserId = item.child("users").child("destination").getValue(String.class);
                        chatList.add(item.getValue(ChatData.Comment.class));
                    }
                }
                Log.e(TAG, "onChildAdded: item: "+chatList);
                refreshData();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void refreshData() {
        if(userMessageAdapter==null){
            userMessageRecyclerView.setAdapter(new UserMessageAdapter(getApplicationContext(), userMessageList));
        }else{
            userMessageRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.close_btn3:
                finish();
                break;
        }
    }
}
