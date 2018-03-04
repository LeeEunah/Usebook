package com.example.eunah.eosproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView welcomeUserTxt;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        welcomeUserTxt = (TextView) findViewById(R.id.welcome_user_txt);

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, StartActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        String userId = user.getEmail().substring(0, index);
        welcomeUserTxt.setText(getResources().getString(R.string.welcome_user, userId));
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
            case R.id.book_shop_btn:
                intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
                break;

            case R.id.message_btn:
                intent = new Intent(getApplicationContext(), UserMessageActivity.class);
                startActivity(intent);
                break;

            case R.id.logout_txt:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, StartActivity.class));
        }
    }
}
