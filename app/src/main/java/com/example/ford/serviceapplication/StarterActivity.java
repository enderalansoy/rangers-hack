package com.example.ford.serviceapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.ford.serviceapplication.R.styleable.View;

public class StarterActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        ImageView img = (ImageView) findViewById(R.id.panicImage);
        img.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //Sent Messages To Family or Friends....
                Toast.makeText(getApplicationContext(),"Kayıtlı Kisilere Bildirimleri Gönderildi.", Toast.LENGTH_LONG).show();
            }
        });
    }


}
