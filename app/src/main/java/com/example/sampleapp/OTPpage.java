package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class OTPpage extends AppCompatActivity {

    EditText phoneM,otptext;
    TextView loginbttn;
    Button send,verify;
    int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_ppage);


        phoneM=findViewById(R.id.mobileNo);
        otptext=findViewById(R.id.OTPField);
        send=findViewById(R.id.sendotp);
        verify=findViewById(R.id.verifyButton);
        loginbttn=findViewById(R.id.loginButtonInOTP);

        loginbttn.setMovementMethod(LinkMovementMethod.getInstance());
        loginbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPpage.this,loginPage.class));
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
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

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(randomNumber==Integer.valueOf(otptext.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Mobile Number is verified successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(OTPpage.this,Register.class));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong OTP",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private  void sendmss()
    {
        String phone=phoneM.getText().toString().trim();
        Random random=new Random();
        randomNumber=random.nextInt(999999);
        String textmess=String.valueOf(randomNumber);

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