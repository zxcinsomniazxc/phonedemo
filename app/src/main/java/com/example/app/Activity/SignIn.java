package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ApiClient;
import com.example.app.R;
import com.example.app.Request.LoginRequest;
import com.example.app.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    EditText edEmail, edPass;
    Button btnSignIn;
    SharedPreferences sPref;

    final String saveg = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||TextUtils.isEmpty(edPass.getText().toString())){
                    String message = "Заполните все поля";
                    Toast.makeText(SignIn.this, message,Toast.LENGTH_LONG).show();
                }else {
                    loginUser();
                }
            }
        });


    }

    public void onClickSignUp (View view)
    {
        Intent signUp = new Intent(SignIn.this, SignUp.class);
        startActivity(signUp);
    }

    public void  loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edEmail.getText().toString());
        loginRequest.setPassword(edPass.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getLogin().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    sPref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    int message = loginResponse.getToken();
                    ed.putString(saveg, String.valueOf(message));
                    ed.commit();



                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    String message = "Что-то пошло не так";
                    Toast.makeText(SignIn.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SignIn.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }



}