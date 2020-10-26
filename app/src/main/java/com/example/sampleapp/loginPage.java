package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class loginPage extends AppCompatActivity {


    EditText username;
    EditText password;
    Button loginBttn;
    TextView registerlnk;

    String _name,_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username=findViewById(R.id.editTextTextPersonName2);
        password=findViewById(R.id.editTextTextPassword);
        loginBttn=findViewById(R.id.loginbutton);
        registerlnk=findViewById(R.id.registerlnk);

        registerlnk.setMovementMethod(LinkMovementMethod.getInstance());
        registerlnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(loginPage.this,OTPpage.class);
                startActivity(intent);
            }
        });

        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name=username.getText().toString();
                _pass=password.getText().toString();
                if(!_name.isEmpty()&&!_pass.isEmpty())
                {
                        FetchData();
                }
                else if(_name.isEmpty())
                {
                    Toast.makeText(loginPage.this,"Please fill username",Toast.LENGTH_LONG).show();
                }
                else if(_pass.isEmpty())
                {
                    Toast.makeText(loginPage.this,"Please fill password",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void FetchData() {


        final HashMap<String,String> params=new HashMap<>();
        params.put("username",_name);
        params.put("password",_pass);

        String apiKey="https://sampleappt.herokuapp.com/api/sample/auth/login";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, apiKey,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getBoolean("success"))
                            {

                                String token=response.getString("token");
                                Toast.makeText(loginPage.this,token,Toast.LENGTH_LONG).show();
                                startActivity(new Intent(loginPage.this,HomeActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse=error.networkResponse;
                if(error instanceof ServerError && networkResponse!=null)
                {
                    try{

                        String res=new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers,
                                "utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(loginPage.this,obj.getString("msg"),Toast.LENGTH_LONG).show();

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

        RequestQueue requestQueue=Volley.newRequestQueue(loginPage.this);
        requestQueue.add(jsonObjectRequest);



    }

}