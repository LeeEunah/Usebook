package com.example.eunah.eosproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "Error";
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.e(TAG, "StartActivity onCreate");

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else{
            startActivity(new Intent(this, SplashActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.back_twice),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.login_btn:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.sign_in_btn:
                intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
                break;
        }
    }
}
