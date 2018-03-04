package com.example.eunah.eosproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.FileNameData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FullView extends AppCompatActivity {
    private static final String TAG = "Error";
    private final int REQ_COVER_IMAGE = 3333;
    private final int REQ_INSIDE_IMAGE = 3334;

    BookData bookData;
    FileNameData fileNameData;

    private ArrayList<BookData> bookDataList = DummyData.bookList;
    private ArrayList<FileNameData> fileNameList = DummyData.fileNameList;
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference coverRef, insideRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        final ImageView fullImageView = (ImageView) findViewById(R.id.full_image_view);
//        Log.e(TAG, "bigCoverImage: "+coverPosition);

        Intent intent = getIntent();
        int coverPosition = intent.getIntExtra("cover_position", 0);
        int insidePosition = intent.getIntExtra("inside_position", 0);

        int coverCode = intent.getIntExtra("cover_code", 0);
        Log.e(TAG, "coverCode: "+coverCode);
        int insideCode = intent.getIntExtra("inside_code", 0);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if (coverCode == REQ_COVER_IMAGE){
            coverRef = storageRef
                    .child("images/"+
                            fileNameList.get(coverPosition).getCoverFileName());

            coverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri).into(fullImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "cover image uri download fail.");
                }
            });
        }

        if (insideCode == REQ_INSIDE_IMAGE){
            insideRef = storageRef.child("images/"+fileNameList.get(insidePosition).getInsideFileName());
            if (insideRef != null){
                insideRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).override(150, 150)
                                .centerCrop().into(fullImageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "inside image uri download fail.");
                    }
                });
            }
        }


    }
}
