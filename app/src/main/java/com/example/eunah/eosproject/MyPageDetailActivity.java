package com.example.eunah.eosproject;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.CheckData;
import com.example.eunah.eosproject.data.DummyData;
import com.example.eunah.eosproject.data.FileNameData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPageDetailActivity extends AppCompatActivity {

    private static final String TAG = "Error";
    private final int REQ_COVER_IMAGE = 3333;
    private final int REQ_INSIDE_IMAGE = 3334;
    private DatabaseReference databaseReference;

    TextView bigTitleTxt, smallTitleTxt, authorTxt, publishTxt, publishDateTxt, priceTxt,
            expectationPriceTxt, idTxt, dateTxt,
            getNoUnderlineTxt, getPencilUnderlineTxt, getPenUnderlineTxt, getNoWriteTxt,
            getPencilWriteTxt, getPenWriteTxt, getCleanCoverTxt, getNoCleanCoverTxt,
            getNoNameTxt, getYesNameTxt, getNoDiscolorTxt, getYesDiscolorTxt, getNoDamageTxt,
            getYesDamageTxt, getYesDeliveryTxt, getNoDeliveryTxt, getYesDirectTxt, getNoDirectTxt,
            getAreaTxt, getExtraExplanationTxt;


    CheckBox underlinePencilCheck, underlinePenCheck, writePencilCheck, writePenCheck,
            coverCleanCheck, noNameCheck, noPageDiscolorCheck, noPageDamageCheck,
            directDealingCheck, deliveryCheck;

    Button soldOutBtn;

    ImageView coverCamera, insideCamera;

    RelativeLayout layout;

    BookData bookData;
    CheckData checkData;
    FileNameData fileNameData;

    FirebaseStorage storage;
    StorageReference storageRef, coverRef, insideRef;

    private int position;
    private ArrayList<BookData> bookDataList = DummyData.bookList;
    private ArrayList<CheckData> checkDataList = DummyData.checkList;
    private ArrayList<FileNameData> fileNameList = DummyData.fileNameList;
    private ArrayList<String> myBookList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_detail);

        layout = (RelativeLayout)findViewById(R.id.layout);

        bigTitleTxt = (TextView)findViewById(R.id.big_title_txt);
        smallTitleTxt = (TextView)findViewById(R.id.small_title_txt);
        authorTxt = (TextView)findViewById(R.id.get_author_txt);
        publishTxt = (TextView)findViewById(R.id.get_publish_txt);
        publishDateTxt = (TextView)findViewById(R.id.get_date_txt);
        priceTxt = (TextView)findViewById(R.id.get_price_txt);
        expectationPriceTxt = (TextView)findViewById(R.id.get_expectation_price_txt);
        idTxt = (TextView)findViewById(R.id.user_id_txt);
        dateTxt = (TextView)findViewById(R.id.date_txt);

        getNoUnderlineTxt = (TextView)findViewById(R.id.get_no_underline_txt);
        getPencilUnderlineTxt = (TextView)findViewById(R.id.get_pencil_underline_txt);
        getPenUnderlineTxt = (TextView)findViewById(R.id.get_pen_underline_txt);
        getNoWriteTxt = (TextView)findViewById(R.id.get_no_write_txt);
        getPencilWriteTxt = (TextView)findViewById(R.id.get_pencil_write_txt);
        getPenWriteTxt = (TextView)findViewById(R.id.get_pen_write_txt);
        getCleanCoverTxt = (TextView)findViewById(R.id.get_clean_cover_txt);
        getNoCleanCoverTxt = (TextView)findViewById(R.id.get_no_clean_cover_txt);
        getNoNameTxt = (TextView)findViewById(R.id.get_no_name_txt);
        getYesNameTxt = (TextView)findViewById(R.id.get_yes_name_txt);
        getNoDiscolorTxt = (TextView)findViewById(R.id.get_no_discolor_txt);
        getYesDiscolorTxt = (TextView)findViewById(R.id.get_yes_discolor_txt);
        getNoDamageTxt = (TextView)findViewById(R.id.get_no_damage_txt);
        getYesDamageTxt = (TextView)findViewById(R.id.get_yes_damage_txt);
        getNoDeliveryTxt = (TextView)findViewById(R.id.get_no_delivery_txt);
        getYesDeliveryTxt = (TextView)findViewById(R.id.get_yes_delivery_txt);
        getNoDirectTxt = (TextView)findViewById(R.id.get_no_direct_txt);
        getYesDirectTxt = (TextView)findViewById(R.id.get_yes_direct_txt);
        getAreaTxt = (TextView)findViewById(R.id.get_area_txt);
        getExtraExplanationTxt = (TextView)findViewById(R.id.get_extra_explanation_txt);

        underlinePencilCheck = (CheckBox)findViewById(R.id.underline_pencil_check);
        underlinePenCheck = (CheckBox)findViewById(R.id.underline_pen_check);
        writePencilCheck = (CheckBox)findViewById(R.id.write_pencil_check);
        writePenCheck = (CheckBox)findViewById(R.id.write_pen_check);
        coverCleanCheck = (CheckBox)findViewById(R.id.cover_clean_check);
        noNameCheck = (CheckBox)findViewById(R.id.no_name_check);
        noPageDiscolorCheck = (CheckBox)findViewById(R.id.no_page_discolor_check);
        noPageDamageCheck = (CheckBox)findViewById(R.id.no_page_damage_check);
        directDealingCheck = (CheckBox)findViewById(R.id.direct_dealing_check);
        deliveryCheck = (CheckBox)findViewById(R.id.delivery_check);

        soldOutBtn = (Button)findViewById(R.id.sold_out_btn);

        coverCamera = (ImageView)findViewById(R.id.cover_camera);
        insideCamera = (ImageView)findViewById(R.id.inside_camera);

        bookData = new BookData();
        checkData = new CheckData();
        fileNameData = new FileNameData();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        Log.e(TAG, "onCreate: check: "+checkDataList.get(position).isSoldOrNot() );

        if(checkDataList.get(position).isSoldOrNot() == true){
            soldOutBtn.setEnabled(false);
            soldOutBtn.setText(R.string.book_is_sold_out);
            soldOutBtn.setBackgroundColor(getResources().getColor(R.color.nextBtnColor));
        }

        if(fileNameList.get(position).getInsideFileName() == null){
            insideCamera.setEnabled(false);
        }

        getBookInfo(position);
        checkColor(position);
    }

    public void getBookInfo(final int position){
        bigTitleTxt.setText(bookDataList.get(position).getdTitle());
        smallTitleTxt.setText(bookDataList.get(position).getdTitle());
        authorTxt.setText(bookDataList.get(position).getdWriter());
        publishTxt.setText(bookDataList.get(position).getdPublish());
        publishDateTxt.setText(bookDataList.get(position).getdDate());
        priceTxt.setText(bookDataList.get(position).getdPrice()+" 원");
        expectationPriceTxt.setText(bookDataList.get(position).getdExpectationPrice()+" 원");
        idTxt.setText(bookDataList.get(position).getId());
        priceTxt.setPaintFlags(priceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        getAreaTxt.setText(bookDataList.get(position).getArea());
        getExtraExplanationTxt.setText(bookDataList.get(position).getExtraExplanation());
        dateTxt.setText(bookDataList.get(position).getCurrentDate());

        Log.e(TAG, "uri: "+fileNameList);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        coverRef = storageRef
                .child("images/"+
                        fileNameList.get(position).getCoverFileName());
        coverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri)
                        .override(150, 150).centerCrop().into(coverCamera);
                Log.e(TAG, "coverRef: "+coverRef);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "cover image uri download fail.");
            }
        });

        insideRef = storageRef.child("images/"+fileNameList.get(position).getInsideFileName());
        insideRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).override(150, 150)
                        .centerCrop().into(insideCamera);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "inside image uri download fail.");
            }
        });
    }

    public void checkColor(int position){
        if (checkDataList.get(position).isUnderlinePencil())
            getPencilUnderlineTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else if (checkDataList.get(position).isUnderlinePen())
            getPencilUnderlineTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getNoUnderlineTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isWritePencil())
            getPencilWriteTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else if (checkDataList.get(position).isWritePen())
            getPenWriteTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getNoWriteTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isCoverClean())
            getCleanCoverTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getNoCleanCoverTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isNoName())
            getNoNameTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getYesNameTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isNoPageColor())
            getNoDiscolorTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getYesDiscolorTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isNoPageRuin())
            getNoDamageTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getYesDamageTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isDelivery())
            getYesDeliveryTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getNoDeliveryTxt.setTextColor(getResources().getColor(R.color.blackTxt));

        if (checkDataList.get(position).isDirect())
            getYesDirectTxt.setTextColor(getResources().getColor(R.color.blackTxt));
        else
            getNoDirectTxt.setTextColor(getResources().getColor(R.color.blackTxt));
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.cover_camera:
                intent = new Intent(getApplicationContext(), FullView.class);
                intent.putExtra("cover_position", position);
                intent.putExtra("cover_code", REQ_COVER_IMAGE);
                startActivity(intent);
                break;

            case R.id.inside_camera:
                intent = new Intent(getApplicationContext(), FullView.class);
                intent.putExtra("inside_position", position);
                intent.putExtra("inside_code", REQ_INSIDE_IMAGE);
                startActivity(intent);
                break;

            case R.id.home_btn:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.close_btn:
                finish();
                break;

            case R.id.sold_out_btn:
//                checkDataList.get(position).setSoldOrNot(true);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                int index = firebaseUser.getEmail().indexOf("@");
                final String fireBaseUserId = firebaseUser.getEmail().substring(0, index);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("book");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        myBookList.clear();
                       for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
                           String userId = fileSnapshot.child("id").getValue(String.class);
                           if(fireBaseUserId.equals(userId)){
                               myBookList.add(fileSnapshot.getKey());
                           }
//                           if(position == i){
//                               myBookList.add(fileSnapshot.getKey());
//                           }
//                           i++;
                       }
                        Log.e(TAG, "onDataChange: myBookList: "+myBookList.get(0) );
                        databaseReference.child(myBookList.get(position)).child("checkData").child("soldOrNot").setValue(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(this, R.string.sold_out, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
