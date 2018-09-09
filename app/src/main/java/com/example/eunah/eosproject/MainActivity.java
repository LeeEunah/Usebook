package com.example.eunah.eosproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView welcomeUserTxt;
    private Button languageBtn;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    private static final String TAG = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        welcomeUserTxt = (TextView) findViewById(R.id.welcome_user_txt);
        languageBtn = findViewById(R.id.language_btn);

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
                break;

            case R.id.language_btn:
                setLanguage();
                break;
        }
    }

    public void setLanguage(){
        Locale currentLocale = getResources().getConfiguration().locale;
        String language = currentLocale.getLanguage();

        if (language == "en"){
            Locale locale = new Locale("ko");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        if (language == "ko"){
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        restartActivity();
    }

    public void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
