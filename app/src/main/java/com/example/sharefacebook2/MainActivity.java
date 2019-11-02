package com.example.sharefacebook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btnShareTXT, btnShareIMG;
    ImageView mImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textview);

        btnShareIMG=findViewById(R.id.btnShareIMG);
        btnShareTXT=findViewById(R.id.btnShareTXT);
        mImageview=findViewById(R.id.imageview);

        btnShareTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                // sharing intent
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Wirite subject here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, "Share text via"));
            }
        });
        // share image click
        btnShareIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable  = mImageview.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();

                //sharing image
                try {
                    File file= new File(MainActivity.this.getExternalCacheDir(), "batman.jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);

                    //sharing intent
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    startActivity(Intent.createChooser(intent, "Share image vie"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
