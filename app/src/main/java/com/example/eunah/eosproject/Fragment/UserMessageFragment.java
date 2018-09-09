package com.example.eunah.eosproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eunah.eosproject.R;
import com.example.eunah.eosproject.adapter.UserMessageAdapter;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.UserMessageData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by leeeunah on 2018. 9. 3..
 */

public class UserMessageFragment extends Fragment{
    private static final String TAG = "Error";
    private RecyclerView userMessageRecyclerView;
    private UserMessageAdapter userMessageAdapter;
    private ArrayList<UserMessageData> userMessageDataList= DummyData.userMessageList;
    private UserMessageData userMessageData;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private String user, recentDate, recentMessage, destinationUser;
    public String myId, chatDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("###onCreateView:", "UserMessage Fragment");
        View view = inflater.inflate(R.layout.fragment_user_message, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        myId = user.getEmail().substring(0, index);

        userMessageRecyclerView = (RecyclerView) view.findViewById(R.id.user_message_recyclerview);
        userMessageRecyclerView.setHasFixedSize(true);
        userMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserMessageList();
        return view;
    }

    public void UserMessageList(){
        Locale locale = getResources().getConfiguration().locale;
        if (locale.getLanguage() == "en")
            chatDatabase = "chats_en";
        if (locale.getLanguage() == "ko")
            chatDatabase = "chats_ko";

        userMessageDataList.clear();
        firebaseDatabase.getReference().child(chatDatabase).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.e(TAG, "data: "+dataSnapshot);
                destinationUser = dataSnapshot.child("users").child("destination").getValue(String.class);
                user = dataSnapshot.child("users").child("user").getValue(String.class);
                if (myId.equals(destinationUser) || myId.equals(user)){
                    for (DataSnapshot contents : dataSnapshot.child("comments").getChildren()){
                        recentDate = contents.child("date").getValue(String.class);
                        recentMessage = contents.child("message").getValue(String.class);
                    }
                    userMessageData = new UserMessageData(recentDate, destinationUser, user, myId, recentMessage);
                    userMessageDataList.add(userMessageData);
                }
                Log.e(TAG, "Data: "+ userMessageDataList.size());
                refreshData();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                destinationUser = dataSnapshot.child("users").child("destination").getValue(String.class);
                user = dataSnapshot.child("users").child("user").getValue(String.class);
                if (myId.equals(destinationUser) || myId.equals(user)){
                    for (DataSnapshot contents : dataSnapshot.child("comments").getChildren()){
                        recentDate = contents.child("date").getValue(String.class);
                        recentMessage = contents.child("message").getValue(String.class);
                    }
                    userMessageData.setDate(recentDate);
                    userMessageData.setMessage(recentMessage);
                }
                refreshData();
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
//        firebaseDatabase.getReference().child(chatDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot item : dataSnapshot.getChildren()){
//                    destinationUser = item.child("users").child("destination").getValue(String.class);
//                    user = item.child("users").child("user").getValue(String.class);
//                    if (myId.equals(destinationUser) || myId.equals(user)){
//                        for (DataSnapshot contents : item.child("comments").getChildren()){
//                            recentDate = contents.child("date").getValue(String.class);
//                            recentMessage = contents.child("message").getValue(String.class);
//                        }
//                        userMessageData = new UserMessageData(recentDate, destinationUser, user, myId, recentMessage);
//                        userMessageDataList.add(userMessageData);
//                    } else continue;
//                }
//
//                Log.e(TAG, "Data: "+ userMessageDataList.size());
//                refreshData();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData() {
        if (userMessageAdapter == null) {
            userMessageRecyclerView.setAdapter(new UserMessageAdapter(getActivity(), userMessageDataList));
        } else{
            userMessageRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
