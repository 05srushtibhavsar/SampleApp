package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sampleapp.UtilService.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Register extends AppCompatActivity {

    Button registerbttn;
    EditText username;
    EditText mobileNo;
    EditText email;
    EditText pass;
    EditText confirmPass;
    TextView loginlnk;

    String _name,_mobile,_email,_pass,_confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbttn=findViewById(R.id.regButton);
         username=findViewById(R.id.editTextTextPersonName3);
         mobileNo=findViewById(R.id.editTextPhone2);
         email=findViewById(R.id.editTextTextEmailAddress);
         pass=findViewById(R.id.editTextTextPassword2);
         confirmPass=findViewById(R.id.editTextTextPassword3);
         loginlnk=findViewById(R.id.LogintextView);



        registerbttn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(final View view) {

                 _name=username.getText().toString();
                 _mobile=mobileNo.getText().toString();
                 _email=email.getText().toString();
                 _pass=pass.getText().toString();
                 _confirmPass=confirmPass.getText().toString();

                 if(!_name.isEmpty()&&!_mobile.isEmpty()&&!_email.isEmpty()&&!_pass.isEmpty()&&!_confirmPass.isEmpty())
                 {
                     if(_pass.equals(_confirmPass))
                     {
                         FetchData();
                     }
                     else {
                         Toast.makeText(Register.this,"Password not matched",Toast.LENGTH_LONG).show();

                     }
                 }
                 else
                 {
                     Toast.makeText(Register.this,"Please fill all details",Toast.LENGTH_LONG).show();
                 }


             }
         });

         loginlnk.setMovementMethod(LinkMovementMethod.getInstance());
         loginlnk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(Register.this,loginPage.class);
                 startActivity(intent);
             }
         });
    }

    private void FetchData() {

        final HashMap<String,String> params=new HashMap<>();
        params.put("username",_name);
        params.put("phone",_mobile);
        params.put("email",_email);
        params.put("password",_pass);

        String apiKey="https://sampleappt.herokuapp.com/api/sample/auth/register";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, apiKey,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getBoolean("success"))
                            {

                                String token=response.getString("token");
                                Toast.makeText(Register.this,token,Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this,HomeActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse=error.networkResponse;
                if(error instanceof ServerError&& networkResponse!=null)
                {
                    try{

                        String res=new String(networkResponse.data,HttpHeaderParser.parseCharset(networkResponse.headers,
                                "utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(Register.this,obj.getString("msg"),Toast.LENGTH_LONG).show();

                    }catch (JSONException | UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("Content-Type","application/json");
                return params;
            }
        };

        int socketTime=3000;
        RetryPolicy policy= new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(policy);

        RequestQueue requestQueue=Volley.newRequestQueue(Register.this);
        requestQueue.add(jsonObjectRequest);



    }


}