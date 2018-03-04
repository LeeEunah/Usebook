package com.example.eunah.eosproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Error";

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    EditText loginIdEdit, loginPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        loginIdEdit = (EditText)findViewById(R.id.login_id_edit);
        loginPasswordEdit = (EditText)findViewById(R.id.login_password_edit);

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        loginPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        PasswordTransformationMethod passwdfm = new PasswordTransformationMethod();
        loginPasswordEdit.setTransformationMethod(passwdfm);
    }

    private void userLogin(){
        String id = loginIdEdit.getText().toString().trim();
        String password = loginPasswordEdit.getText().toString().trim();

        if(TextUtils.isEmpty(id)){
            Toast.makeText(this, getResources().getString(R.string.empty_id), Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getResources().getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "LoginUserWithEmail: onComplete: "+task.isSuccessful());
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Log.e(TAG, "LoginUserWithEmail: onFail: "+task.isSuccessful());
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }
                });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.login2_btn:
                userLogin();
                break;

            case R.id.sign_in_txt:
                finish();
                startActivity(new Intent(this, SignActivity.class));
                break;
        }
    }
}
