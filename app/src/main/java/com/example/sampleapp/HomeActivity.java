package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    private EditText text;
    private EditText num;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        num=findViewById(R.id.editTextPhone3);
        text=findViewById(R.id.editTextTextMultiLine);
        btn=findViewById(R.id.sendButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                    {
                        sendmss();
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }

            }
        });

    }

    private  void sendmss()
    {
        String phone=num.getText().toString().trim();
        String textmess=text.getText().toString().trim();

        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,textmess,null,null);

            Toast.makeText(this,"Send",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"failed "+e,Toast.LENGTH_SHORT).show();

        }

    }
}