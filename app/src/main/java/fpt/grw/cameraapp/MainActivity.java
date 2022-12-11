package fpt.grw.cameraapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnCameraActions;
    EditText inputUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this);
        int totalImg = db.getImgs().size();

        imageView = findViewById(R.id.image_capture);
        btnCameraActions = findViewById(R.id.btn_cameraActions);
        inputUrl = findViewById(R.id.inputUrl);

        //Request For Camera Permission
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
//        https://manofmany.com/wp-content/uploads/2018/09/Tom-Shelby.jpg
//        https://static.timesofisrael.com/www/uploads/2017/04/gal-gadot-1024x640.jpg
        // https://znews-photo.zingcdn.me/w660/Uploaded/mfnuy/2022_11_06/ronaldo_manchester_united_8761.jpg
        btnCameraActions.setOnClickListener(view -> {
            final String[]  options = {"Take photo","View a picture from Url","List Image"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(options, (dialog, item) ->{
                if(options[item] == "Take photo"){
                    //Open Camera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityIfNeeded(intent, 100);
                }else if(options[item] == "View a picture from Url"){
                    try {
                        String url = inputUrl.getText().toString();
                        Image img = new Image(url);
                        db.createImg(img);
                        Picasso.get()
                                .load(url)
                                .into(imageView);
                    }catch (Exception error){
                        Toast.makeText(this, "Invalid URL, please enter url again!", Toast.LENGTH_SHORT).show();
                    }
                }else if(options[item] == "List Image"){
                    if(totalImg < 1){
                        Toast.makeText(this, "Dont have any image!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent listView = new Intent(MainActivity.this, ViewListImage.class);
                    startActivity(listView);
                }
            });
            builder.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            //Get Capture Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");

            //Set Capture Image to ImageView
            imageView.setImageBitmap(captureImage);
        }
    }
}