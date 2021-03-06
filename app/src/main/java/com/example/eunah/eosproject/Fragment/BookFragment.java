package com.example.eunah.eosproject.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eunah.eosproject.R;
import com.example.eunah.eosproject.StoreActivity;
import com.example.eunah.eosproject.adapter.StoreAdapter;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.CheckData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.FileNameData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by leeeunah on 2018. 2. 11..
 */

public class BookFragment extends Fragment{
    private static final String TAG = "Error";
    private RecyclerView bookRecyclerview;
    private StoreAdapter storeAdapter;
    private TextView textView, textView2;
    private BookData bookData;
    private CheckData checkData;
    private FileNameData fileNameData;
    private ArrayList<BookData> bookDataList = DummyData.bookList;
    private ArrayList<CheckData> checkDataList = DummyData.checkList;
    private ArrayList<FileNameData> fileNameDataList = DummyData.fileNameList;
    private DatabaseReference databaseReference;
    private String bookDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        Log.e(TAG, "onCreatView: "+"book fragment");
        bookRecyclerview = (RecyclerView)view.findViewById(R.id.book_list_recyclerview);
        textView = (TextView)view.findViewById(R.id.textView2);
        textView2 = (TextView)view.findViewById(R.id.textView22);

        bookRecyclerview.setHasFixedSize(true);
        bookRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataRecall();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreate: "+"BookFragment");
    }

    @Override
    public void onResume() {
        super.onResume();

//        refreshData();

        Log.e("###ON RESUME", "Book Fragment");
    }

    private void refreshData() {
        if(storeAdapter==null){
            bookRecyclerview.setAdapter(new StoreAdapter(getContext(), bookDataList, checkDataList, fileNameDataList));
        }else{
            bookRecyclerview.getAdapter().notifyDataSetChanged();
        }
    }

    public void dataRecall(){
        Log.e(TAG, "dataRecall");
        Locale locale = getResources().getConfiguration().locale;
        if (locale.getLanguage() == "en")
            bookDatabase = "books_en";
        if (locale.getLanguage() == "ko")
            bookDatabase = "books_ko";

        bookDataList.clear();
        checkDataList.clear();
        fileNameDataList.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(bookDatabase)) {
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        Boolean underlinePencil = fileSnapshot.child("checkData").child("underlinePencil").getValue(Boolean.class);
                        Boolean underlinePen = fileSnapshot.child("checkData").child("underlinePen").getValue(Boolean.class);
                        Boolean writePencil = fileSnapshot.child("checkData").child("writePencil").getValue(Boolean.class);
                        Boolean writePen = fileSnapshot.child("checkData").child("writePen").getValue(Boolean.class);
                        Boolean coverClean = fileSnapshot.child("checkData").child("coverClean").getValue(Boolean.class);
                        Boolean noName = fileSnapshot.child("checkData").child("noName").getValue(Boolean.class);
                        Boolean noPageColor = fileSnapshot.child("checkData").child("noPageColor").getValue(Boolean.class);
                        Boolean noPageRuin = fileSnapshot.child("checkData").child("noPageRuin").getValue(Boolean.class);
                        Boolean direct = fileSnapshot.child("checkData").child("direct").getValue(Boolean.class);
                        Boolean delivery = fileSnapshot.child("checkData").child("delivery").getValue(Boolean.class);
                        Boolean soldOrNot = fileSnapshot.child("checkData").child("soldOrNot").getValue(Boolean.class);

                        String title = fileSnapshot.child("dTitle").getValue(String.class);
                        String author = fileSnapshot.child("dWriter").getValue(String.class);
                        String publish = fileSnapshot.child("dPublish").getValue(String.class);
                        String date = fileSnapshot.child("dDate").getValue(String.class);
                        String price = fileSnapshot.child("dPrice").getValue(String.class);
                        String expectationPrice = fileSnapshot.child("dExpectationPrice").getValue(String.class);
                        String id = fileSnapshot.child("id").getValue(String.class);

                        String area = fileSnapshot.child("area").getValue(String.class);
                        String extraExplanation = fileSnapshot.child("extraExplanation").getValue(String.class);
                        String currentDate = fileSnapshot.child("currentDate").getValue(String.class);
                        String coverImage = fileSnapshot.child("dCoverImage").getValue(String.class);
                        String insideImage = fileSnapshot.child("dInsideImage").getValue(String.class);

                        String coverFileName = fileSnapshot.child("fileName").child("coverFileName").getValue(String.class);
                        String insideFileName = fileSnapshot.child("fileName").child("insideFileName").getValue(String.class);

                        Log.e(TAG, "bookData: " + title);
                        bookData = new BookData(id, title, author, publish, date, price, expectationPrice,
                                area, extraExplanation, currentDate, coverImage, insideImage);

                        checkData = new CheckData(underlinePencil, underlinePen, writePencil, writePen,
                                coverClean, noName, noPageColor, noPageRuin, direct, delivery, soldOrNot);
                        checkDataList.add(checkData);


                        bookDataList.add(bookData);
                        fileNameData = new FileNameData(coverFileName, insideFileName);
                        fileNameDataList.add(fileNameData);

                        Collections.reverse(bookDataList);
                        Collections.reverse(checkDataList);
                        Collections.reverse(fileNameDataList);
                    }
                }
//                if(bookDataList.size() == 0){
//                    Log.e(TAG, "zero");
//                    textView2.setText(getResources().getString(R.string.no_my_book));
//                }
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

}
