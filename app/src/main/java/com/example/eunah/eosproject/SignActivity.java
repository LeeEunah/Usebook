package com.example.eunah.eosproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Error";

    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    EditText signIdEdit, signPasswordEdit;
    TextView password_warning;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Log.d(TAG, "onCreate");
        progressDialog = new ProgressDialog(this);
        signIdEdit = (EditText)findViewById(R.id.sign_in_id_edit);
        signPasswordEdit = (EditText)findViewById(R.id.sign_in_password_edit);
        password_warning = (TextView)findViewById(R.id.limit_password_txt);
        joinBtn = (Button)findViewById(R.id.join_in_btn);

        joinBtn.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Log.e(TAG, "onAuthStateChange: signed");
                    startActivity(new Intent(SignActivity.this, MainActivity.class));
                    finish();
                }else{
                    Log.e(TAG, "onAuthStateChange: signed_out");
                }
            }
        };

        signPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        PasswordTransformationMethod passwdfm = new PasswordTransformationMethod();
        signPasswordEdit.setTransformationMethod(passwdfm);
    }

    private void registerUser() {
        String id = signIdEdit.getText().toString();
        String password = signPasswordEdit.getText().toString();

        Log.e(TAG, "id: "+id+ " password: "+password);


        if(TextUtils.isEmpty(id)){
            Toast.makeText(this, getResources().getString(R.string.empty_id), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getResources().getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6) {
            signPasswordEdit.setText(null);
            password_warning.setTextColor(Color.parseColor("#FFDE6464"));
            return;
        }

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "createUserWithEmail: onComplete: "+task.isSuccessful());

                        if(!task .isSuccessful()){
                            Toast.makeText(SignActivity.this, getResources().getString(R.string.already_regist)
                            , Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "failed: "+task.getException());
                        }else{
                            Log.e(TAG, "sign in success");
                            startActivity(new Intent(SignActivity.this, MainActivity.class));
                            finish();
                        }
                            progressDialog.hide();
                        }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
        if(mAuthListener != null){
           firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onClick(View view){
        if(view == joinBtn){
            registerUser();
        }
    }
}
