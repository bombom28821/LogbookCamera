package fpt.grw.cameraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewListImage extends AppCompatActivity {
    static int index = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_image);
        DatabaseHelper db = new DatabaseHelper(this);
        int totalImg = db.getImgs().size();
        Image imgShow = db.getImg(index);

        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageViewPrev = findViewById(R.id.imageViewPrev);
        ImageView imageViewNext = findViewById(R.id.imageViewNext);

        Button btnBack = findViewById(R.id.btnBack);
        Picasso.get()
                .load(imgShow.getImg_url())
                .into(imageView);
        if(index == 1){
            imageViewPrev.setVisibility(View.INVISIBLE);
        }
        if(totalImg == 1){
            imageViewPrev.setVisibility(View.INVISIBLE);
            imageViewNext.setVisibility(View.INVISIBLE);
        }

        //Click Back
        btnBack.setOnClickListener(view -> {
            Intent back = new Intent(ViewListImage.this, MainActivity.class);
            startActivity(back);
        });

        //Click BtnPrev
        imageViewPrev.setOnClickListener(view -> {
            index = index - 1;
            if(index == 1){
                imageViewPrev.setVisibility(View.INVISIBLE);
            }
            imageViewNext.setVisibility(View.VISIBLE);
            Image imgShowPrev = db.getImg(index);
            Picasso.get()
                    .load(imgShowPrev.getImg_url())
                    .into(imageView);
        });

        //Click BtnNext
        imageViewNext.setOnClickListener(view -> {
            index = index + 1;
            if(index == totalImg){
                imageViewNext.setVisibility(View.INVISIBLE);
            }
            imageViewPrev.setVisibility(View.VISIBLE);
            Image imgShowNext = db.getImg(index);
            Picasso.get()
                    .load(imgShowNext.getImg_url())
                    .into(imageView);
        });
    }
}