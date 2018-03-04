package com.example.eunah.eosproject;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.eunah.eosproject.Fragment.BookFragment;
import com.example.eunah.eosproject.Fragment.MyPageFragment;

public class MyPageActivity extends AppCompatActivity {
    private static final String TAG = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCrete: MyPageActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        FragmentManager myPageFragmentManager = getSupportFragmentManager();
        if (myPageFragmentManager != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_page_fragment, new MyPageFragment()).commit();
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.close2_btn:
                finish();
                break;
        }
    }
}
