package com.example.eunah.eosproject;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.CheckData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.FileNameData;
import com.example.eunah.eosproject.data.Manager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellActivity extends AppCompatActivity {

    private static final String TAG = "Error";
    private final int REQ_CODE_COVER_IMAGE = 1111;
    private final int REQ_CODE_INSIDE_IMAGE = 1112;
    private final int READ_STORAGE_REQ_CODE = 2222;

    BookData bookData, addBook;
    CheckData checkData, addCheck;
    FileNameData addFileName;
    LinearLayout writeTrace, preserveState, realPicture,
            expectationPrice, tradeWay, directTradeArea, extraExplanation;
    ScrollView sellScrollView;
    EditText bookNameEdit, bookAuthorEdit, bookPublishEdit, bookDateEdit, bookPriceEdit,
            expectationPriceEdit, areaEdit, extraExplanationEdit;
    CheckBox underlinePencil, underlinePen, writePencil, writePen, coverClean, noName,
             noPageColor, noPageRuin, direct, delivery;
    ImageView coverImage, insideImage;

    String userId;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseBook;

    private Uri filePath;
    private String coverFileName, insideFileName, book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        int index = user.getEmail().indexOf("@");
        userId = user.getEmail().substring(0, index);
        databaseBook = FirebaseDatabase.getInstance().getReference("book");
        book_id = databaseBook.push().getKey();

        writeTrace = (LinearLayout)findViewById(R.id.write_trace_layout);
        preserveState = (LinearLayout)findViewById(R.id.preserve_state_layout);
        realPicture = (LinearLayout)findViewById(R.id.real_picture_layout);
        expectationPrice = (LinearLayout)findViewById(R.id.expectation_price_layout);
        tradeWay = (LinearLayout)findViewById(R.id.trade_way_layout);
        directTradeArea = (LinearLayout)findViewById(R.id.direct_trade_area_layout);
        extraExplanation = (LinearLayout)findViewById(R.id.extra_explanation_layout);

        sellScrollView = (ScrollView)findViewById(R.id.sell_scroll);

        bookNameEdit = (EditText)findViewById(R.id.book_name_edit);
        bookAuthorEdit = (EditText)findViewById(R.id.book_author_edit);
        bookPublishEdit = (EditText)findViewById(R.id.book_publish_edit);
        bookDateEdit = (EditText) findViewById(R.id.book_date_edit);
        bookPriceEdit = (EditText)findViewById(R.id.book_price_edit);
        expectationPriceEdit = (EditText)findViewById(R.id.expectation_price_edit);
        areaEdit = (EditText)findViewById(R.id.area_edit);
        extraExplanationEdit = (EditText)findViewById(R.id.extra_explanation_edit);

        underlinePencil = (CheckBox)findViewById(R.id.underline_pencil_check);
        underlinePen = (CheckBox) findViewById(R.id.underline_pen_check);
        writePencil = (CheckBox) findViewById(R.id.write_pencil_check);
        writePen = (CheckBox)findViewById(R.id.write_pen_check);
        coverClean = (CheckBox)findViewById(R.id.cover_clean_check);
        noName = (CheckBox) findViewById(R.id.no_name_check);
        noPageColor = (CheckBox)findViewById(R.id.no_page_discolor_check);
        noPageRuin = (CheckBox)findViewById(R.id.no_page_damage_check);
        direct = (CheckBox)findViewById(R.id.direct_dealing_check);
        delivery = (CheckBox)findViewById(R.id.delivery_check);

        coverImage = (ImageView)findViewById(R.id.cover_image);
        insideImage = (ImageView)findViewById(R.id.inside_image);

        checkData = new CheckData();
        bookData = new BookData();

        bookNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bookAuthorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdWriter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bookPublishEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdPublish(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bookDateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdDate(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bookPriceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdPrice(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        expectationPriceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setdExpectationPrice(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        areaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setArea(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        extraExplanationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookData.setExtraExplanation(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void selectGallery(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_REQ_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, code);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMHH_mmss");
        Date now = new Date();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_COVER_IMAGE:
                    String coverImagePath = Manager.getPath(this, data.getData());
                    coverFileName = "cover_"+coverImagePath
                            .substring(coverImagePath.lastIndexOf("/")+1, coverImagePath.lastIndexOf("."))
                            +"-"+formatter.format(now)+".jpg";
                    Glide.with(this).load(coverImagePath)
                            .override(100, 120).centerCrop()
                            .into(coverImage);
                    coverImage.bringToFront();
                    Log.e(TAG, "image update");
                    bookData.setdCoverImage(coverImagePath);
                    Log.e(TAG, "coverFile: "+coverFileName);
                    break;

                case REQ_CODE_INSIDE_IMAGE:
                    String insideImagePath = Manager.getPath(this, data.getData());
                    insideFileName = "inside_"+insideImagePath
                            .substring(insideImagePath.lastIndexOf("/")+1, insideImagePath.lastIndexOf("."))
                            +"_"+formatter.format(now)+".jpg";
                    Glide.with(this).load(insideImagePath)
                            .override(100, 120).centerCrop()
                            .into(insideImage);
                    insideImage.bringToFront();
                    bookData.setdInsideImage(insideImagePath);
                    Log.e(TAG, "insideFile: "+insideFileName);
                    break;

                default:
                    break;
            }

        }
    }

    private boolean checkBookData(){
        if(TextUtils.isEmpty(bookData.getdTitle())){
            Toast.makeText(this, getResources().getString(R.string.empty_bookname), Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(bookData.getdWriter())){
            Toast.makeText(this, getResources().getString(R.string.empty_bookAuthor), Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(bookData.getdPublish())){
            Toast.makeText(this, getResources().getString(R.string.empty_bookPublish), Toast.LENGTH_LONG).show();
            return false;

        }

        if(TextUtils.isEmpty(bookData.getdDate())){
            Toast.makeText(this, getResources().getString(R.string.empty_bookDate), Toast.LENGTH_LONG).show();
            return false;
        }

        if(bookData.getdDate().length() < 8){
            Toast.makeText(this, getResources().getString(R.string.date_format), Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(bookData.getdPrice())){
            Toast.makeText(this, getResources().getString(R.string.empty_bookPrice), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void addBookAndCheckData(){
        long nowTime = System.currentTimeMillis();
        Date date = new Date(nowTime);
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy년 MM월 dd일");
        String currentDateStr = currentDate.format(date);

        Log.e(TAG, "date: "+currentDateStr);
        addBook = new BookData(userId, bookData.getdTitle(), bookData.getdWriter(), bookData.getdPublish(),
                bookData.getdDate(), bookData.getdPrice(), bookData.getdExpectationPrice(), bookData.getArea(),
                bookData.getExtraExplanation(), currentDateStr, bookData.getdCoverImage(), bookData.getdInsideImage());
        databaseBook.child(book_id).setValue(addBook);
        DummyData.bookList.add(addBook);

        addCheck = new CheckData(checkData.isUnderlinePencil(), checkData.isUnderlinePen(),
                checkData.isWritePencil(), checkData.isWritePen(), checkData.isCoverClean(),
                checkData.isNoName(), checkData.isNoPageColor(), checkData.isNoPageRuin(),
                checkData.isDirect(), checkData.isDelivery(), false);
        databaseBook.child(book_id).child("checkData").setValue(addCheck);
        DummyData.checkList.add(addCheck);
        Log.e(TAG, "add book data");
        Log.e(TAG, "add check data");

        finish();
    }

    private void writeData(){
        if(underlinePencil.isChecked()){
            checkData.setUnderlinePencil(true);
        } else {
            checkData.setUnderlinePencil(false);
        }
//        Log.e(TAG, "isChecked: "+ underlinePencil.isChecked());
        if(underlinePen.isChecked()){
            checkData.setUnderlinePen(true);
        } else {
            checkData.setUnderlinePen(false);
        }

        if(writePencil.isChecked()){
            checkData.setWritePencil(true);
        } else {
            checkData.setWritePencil(false);
        }

        if(writePen.isChecked()){
            checkData.setWritePen(true);
        } else {
            checkData.setWritePen(false);
        }
    }

    private void preserveData(){
        if(coverClean.isChecked()){
            checkData.setCoverClean(true);
        } else {
            checkData.setCoverClean(false);
        }

        if(noName.isChecked()){
            checkData.setNoName(true);
        } else {
            checkData.setNoName(false);
        }

        if(noPageColor.isChecked()){
            checkData.setNoPageColor(true);
        } else {
            checkData.setNoPageColor(false);
        }

        if(noPageRuin.isChecked()){
            checkData.setNoPageRuin(true);
        } else {
            checkData.setNoPageRuin(false);
        }
    }

    private void tradeWayData(){
        if(direct.isChecked()){
            checkData.setDirect(true);
        } else {
            checkData.setDirect(false);
        }

        if(delivery.isChecked()){
            checkData.setDelivery(true);
        } else {
            checkData.setDelivery(false);
        }
    }

    private void writeSkip(){

        checkData.setUnderlinePencil(false);
        checkData.setUnderlinePen(false);
        checkData.setWritePencil(false);
        checkData.setWritePen(false);
//        Log.e(TAG, "set false");

        underlinePencil.setEnabled(false);
        underlinePen.setEnabled(false);
        writePencil.setEnabled(false);
        writePen.setEnabled(false);
//        Log.e(TAG, "setEnabled");
    }

    private void preserveSkip(){

        coverClean.setEnabled(false);
        noName.setEnabled(false);
        noPageColor.setEnabled(false);
        noPageRuin.setEnabled(false);

        checkData.setCoverClean(false);
        checkData.setNoName(false);
        checkData.setNoPageColor(false);
        checkData.setNoPageRuin(false);
    }

    private void scrollDown(){
        sellScrollView.post(new Runnable() {
            @Override
            public void run() {
                sellScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void fileUpload(String filename, String data){
        if (filename != null){
            filePath = Uri.fromFile(new File(data));
            FirebaseStorage storage = FirebaseStorage.getInstance();

            Log.e(TAG, "filename: "+filename);
//            Log.e(TAG, "filePAth: "+filePath);
            StorageReference storageRef = storage.getReferenceFromUrl("gs://usebook-a3f0b.appspot.com")
                    .child("images/"+filename);
            storageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Log.e(TAG, "download: "+ downloadUrl);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "update fail");
                        }
                    });
        }

        addFileName = new FileNameData(coverFileName, insideFileName);
        DummyData.fileNameList.add(addFileName);
        databaseBook.child(book_id).child("fileName").setValue(addFileName);
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.next_btn:
                if(checkBookData()){
                    writeTrace.setVisibility(View.VISIBLE);
                    scrollDown();
                }
                break;

            case R.id.next_btn2:
                writeData();
                if ((checkData.isUnderlinePencil() || checkData.isUnderlinePen()
                        || checkData.isWritePen() || checkData.isWritePencil())){
                    underlinePencil.setEnabled(false);
                    underlinePen.setEnabled(false);
                    writePencil.setEnabled(false);
                    writePen.setEnabled(false);

                    preserveState.setVisibility(View.VISIBLE);
                    scrollDown();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.empty_write),
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.next_btn3:
                preserveData();
                if((checkData.isCoverClean() || checkData.isNoName() || checkData.isNoPageColor()
                || checkData.isNoPageRuin())){
                    coverClean.setEnabled(false);
                    noName.setEnabled(false);
                    noPageColor.setEnabled(false);
                    noPageRuin.setEnabled(false);

                    realPicture.setVisibility(View.VISIBLE);
                    scrollDown();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.empty_preserve),
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.next_btn4:
                if(bookData.getdCoverImage() == null)
                    Toast.makeText(this, getResources().getString(R.string.empty_cover_image),
                            Toast.LENGTH_LONG).show();
                else{
                    expectationPrice.setVisibility(View.VISIBLE);
                    scrollDown();
                }
                break;

            case R.id.next_btn5:
                if(TextUtils.isEmpty(bookData.getdExpectationPrice())){
                    Toast.makeText(this, getResources().getString(R.string.empty_expectationPrice)
                            , Toast.LENGTH_LONG).show();
                    return;
                } else{
                    tradeWay.setVisibility(View.VISIBLE);
                    scrollDown();
                }
                break;

            case R.id.next_btn6:
                tradeWayData();
                if(checkData.isDelivery() || checkData.isDirect()){
                    direct.setEnabled(false);
                    delivery.setEnabled(false);

                    directTradeArea.setVisibility(View.VISIBLE);
                    scrollDown();
                } else {
                 Toast.makeText(this, getResources().getString(R.string.empty_tradeway),
                         Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.next_btn7:
                if(TextUtils.isEmpty(bookData.getArea()))
                    Toast.makeText(this, getResources().getString(R.string.empty_area),
                            Toast.LENGTH_LONG).show();
                else {
                    extraExplanation.setVisibility(View.VISIBLE);
                    scrollDown();
                }
                break;

            case R.id.next_btn8:
                if(TextUtils.isEmpty(bookData.getExtraExplanation()))
                    Toast.makeText(this, getResources().getString(R.string.empty_extra_explanation),
                        Toast.LENGTH_SHORT).show();
                else{
                    addBookAndCheckData();
                    fileUpload(coverFileName, bookData.getdCoverImage());
                    fileUpload(insideFileName, bookData.getdInsideImage());
                }
                Log.e(TAG, "check: "+ checkData.isUnderlinePencil());
                break;

            case R.id.no_applicable_btn1:
                writeSkip();
                preserveState.setVisibility(View.VISIBLE);
                scrollDown();
                break;

            case R.id.no_applicable_btn2:
                preserveSkip();
                realPicture.setVisibility(View.VISIBLE);
                scrollDown();
                break;

            case R.id.skip_btn1:
                if(bookData.getdCoverImage() == null)
                    Toast.makeText(this, getResources().getString(R.string.empty_cover_image),
                            Toast.LENGTH_LONG).show();
                else{
                    expectationPrice.setVisibility(View.VISIBLE);
                    scrollDown();
                }
                break;

            case R.id.skip_btn2:
                bookData.setExtraExplanation("");
                addBookAndCheckData();
                fileUpload(coverFileName, bookData.getdCoverImage());
                fileUpload(insideFileName, bookData.getdInsideImage());
                break;

            case R.id.cover_image:
                selectGallery(REQ_CODE_COVER_IMAGE);
                break;

            case R.id.inside_image:
                selectGallery(REQ_CODE_INSIDE_IMAGE);
                break;

        }
    }
}
