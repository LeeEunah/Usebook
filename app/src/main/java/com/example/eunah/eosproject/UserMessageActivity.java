package com.example.eunah.eosproject;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.eunah.eosproject.Fragment.MyPageFragment;
import com.example.eunah.eosproject.Fragment.UserMessageFragment;
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

    private FirebaseAuth firebaseAuth;

    private ArrayList<UserMessageData> userMessageList = DummyData.userMessageList;
    private ArrayList<ChatData.Comment> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        Log.e(TAG, "onCreate: UserMessageActivity");

        FragmentManager userMessageFragment = getSupportFragmentManager();
        if (userMessageFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.user_message_fragment, new UserMessageFragment()).commit();
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
