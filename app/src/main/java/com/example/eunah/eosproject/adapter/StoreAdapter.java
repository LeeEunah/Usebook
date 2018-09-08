package com.example.eunah.eosproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eunah.eosproject.DetailActivity;
import com.example.eunah.eosproject.R;
import com.example.eunah.eosproject.data.BookData;
import com.example.eunah.eosproject.data.CheckData;
import com.example.eunah.eosproject.data.FileNameData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by leeeunah on 2017. 10. 12..
 */

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int SELL_ITEM_VIEW = 5555;
    public static final int SOLDOUT_ITEM_VIEW = 6666;

    private ArrayList<BookData> bookList = new ArrayList<>();
    private ArrayList<FileNameData> fileNameList = new ArrayList<>();
    private ArrayList<CheckData> checkList = new ArrayList<>();
    private Context context;
    private static final String TAG = "Error";

    public StoreAdapter(Context context, ArrayList<BookData> bookList, ArrayList<CheckData> checkList,
                        ArrayList<FileNameData> fileNameList) {
        this.context = context;
        this.bookList = bookList;
        this.checkList = checkList;
        this.fileNameList = fileNameList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SELL_ITEM_VIEW){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_book_list_item, parent, false);
            return new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sold_out_book_list_item, parent, false);
            return new SoldOutViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final BookData bookData = bookList.get(position);

        if(holder instanceof ViewHolder){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference coverRef = storageRef.child("images/"+fileNameList.get(position).getCoverFileName());

            if(bookData != null){
                ((ViewHolder)holder).bookTitle.setText(bookData.getdTitle());
                ((ViewHolder)holder).bookWriter.setText(context.getResources().getString(R.string.writer, bookData.getdWriter()));
                ((ViewHolder)holder).bookPublish.setText(context.getResources().getString(R.string.publisher, bookData.getdPublish()));
                String date = bookData.getdDate();
                ((ViewHolder)holder).bookDate.setText(context.getResources().getString(R.string.date, date.substring(0, 2), date.substring(2, 4), date.substring(4)));
                ((ViewHolder)holder).bookPrice.setText(context.getResources().getString(R.string.price_won, bookData.getdPrice()));
                ((ViewHolder)holder).bookExpectationPrice.setText(context.getResources().getString(R.string.price_won, bookData.getdExpectationPrice()));
                coverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri).override(100, 145).centerCrop().into(((ViewHolder)holder).bookImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "cover image uri download fail.");
                    }
                });
            }
        }else{
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference coverRef = storageRef.child("images/"+fileNameList.get(position).getCoverFileName());

            if(bookData != null){
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter gray = new ColorMatrixColorFilter(matrix);

                ((SoldOutViewHolder)holder).bookTitle2.setText(bookData.getdTitle());
                ((SoldOutViewHolder)holder).bookWriter2.setText(context.getResources().getString(R.string.writer, bookData.getdWriter()));
                ((SoldOutViewHolder)holder).bookPublish2.setText(context.getResources().getString(R.string.publisher, bookData.getdPublish()));
                String date = bookData.getdDate();
                ((SoldOutViewHolder)holder).bookDate2.setText(context.getResources().getString(R.string.date, date.substring(0, 2), date.substring(2, 4), date.substring(4)));
                ((SoldOutViewHolder)holder).bookPrice2.setText(context.getResources().getString(R.string.price_won, bookData.getdPrice()));
                ((SoldOutViewHolder)holder).bookExpectationPrice2.setText(context.getResources().getString(R.string.price_won, bookData.getdExpectationPrice()));
                ((SoldOutViewHolder)holder).bookImage2.setColorFilter(gray);

                coverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri).override(100, 145).centerCrop().into(((SoldOutViewHolder)holder).bookImage2);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "cover image uri download fail.");
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(checkList.get(position).isSoldOrNot() == false){
            return SELL_ITEM_VIEW;
        }else{
            return SOLDOUT_ITEM_VIEW;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView bookImage;
        TextView bookTitle, bookPublish, bookWriter, bookDate, bookPrice, bookExpectationPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bookImage = (ImageView) itemView.findViewById(R.id.book_image_view);
            bookTitle = (TextView) itemView.findViewById(R.id.title_text);
            bookPublish = (TextView) itemView.findViewById(R.id.publish_text);
            bookWriter = (TextView) itemView.findViewById(R.id.writer_text);
            bookDate = (TextView) itemView.findViewById(R.id.date_text);
            bookPrice = (TextView) itemView.findViewById(R.id.get_price_txt);
            bookExpectationPrice = (TextView) itemView.findViewById(R.id.expectation_price_txt);
            bookPrice.setPaintFlags(bookPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("position", getAdapterPosition());
            view.getContext().startActivity(intent);
        }
    }

    class SoldOutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView bookImage2;
        TextView soldOutTxt, bookTitle2, bookPublish2, bookWriter2, bookDate2, bookPrice2, bookExpectationPrice2;

        public SoldOutViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            bookImage2 = itemView.findViewById(R.id.book_image_view2);
            bookTitle2 = itemView.findViewById(R.id.title_text2);
            bookPublish2 = itemView.findViewById(R.id.publish_text2);
            bookWriter2 = itemView.findViewById(R.id.writer_text2);
            bookDate2 = itemView.findViewById(R.id.date_text2);
            bookPrice2 = itemView.findViewById(R.id.get_price_txt2);
            bookExpectationPrice2 = itemView.findViewById(R.id.expectation_price_txt2);
            bookPrice2.setPaintFlags(bookPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            soldOutTxt = itemView.findViewById(R.id.sold_out_txt);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("position", getAdapterPosition());
            view.getContext().startActivity(intent);
        }
    }
}
