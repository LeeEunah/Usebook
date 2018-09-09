package com.example.eunah.eosproject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eunah.eosproject.Fragment.BookFragment;
import com.example.eunah.eosproject.Fragment.SearchFragment;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.CheckData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.FileNameData;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Locale;

public class StoreActivity extends FragmentActivity{
    private static final String TAG = "Error";

    private ArrayList<BookData> bookDataList = DummyData.bookList;
    private ArrayList<CheckData> checkDataList = DummyData.checkList;
    private ArrayList<FileNameData> fileNameDataList = DummyData.fileNameList;

    private EditText searchEdit;
    private static String searchTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Log.e(TAG, "onCreate: StoreActivity");

        bookDataList.clear();
        checkDataList.clear();
        fileNameDataList.clear();

        searchEdit = (EditText)findViewById(R.id.search_edit);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTxt = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_GO:
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

                        onSearchFragmentChanged();
                        break;
                }
                return true;
            }
        });
    }

    public void onBookFragmentChanged(){
        FragmentManager bookFragmentManager = getSupportFragmentManager();
        if (bookFragmentManager != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.book_store_fragment, new BookFragment()).commit();
        }
    }

    public void onSearchFragmentChanged(){
        FragmentManager searchFragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        Log.e(TAG, "searchTxt: "+searchTxt);
        bundle.putString("word", searchTxt);
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);

        if (searchFragmentManager != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.book_store_fragment, searchFragment)
                    .addToBackStack(null).commit();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: StoreActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: StoreActivity");
        onBookFragmentChanged();
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.sold_btn:
                intent = new Intent(getApplicationContext(), SellActivity.class);
                startActivity(intent);
                break;

            case R.id.mypage_btn:
                intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
                break;
        }
    }
}