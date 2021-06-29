package com.example.app.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.API.ApiClient;
import com.example.app.R;
import com.example.app.Request.RegisterRequest;
import com.example.app.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText firstName, lastName, email, password, repeatPassword;
    TextView goToSignIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString())){
                    ShowDialogWindow("Заполните все поля");
                }
                else{
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        if(password.getText().toString().equals(repeatPassword.getText().toString())){
                            RegisterRequest registerRequest = new RegisterRequest();
                            registerRequest.setEmail(email.getText().toString());
                            registerRequest.setPassword(password.getText().toString());
                            registerRequest.setFirstName(firstName.getText().toString());
                            registerRequest.setLastName(lastName.getText().toString());
                            RegisterUser(registerRequest);
                        }
                        else{
                            ShowDialogWindow("Пароли не совпадают");
                        }
                    }
                    else{
                        ShowDialogWindow("Электронная почта указана неверно");
                    }
                }
            }
        });
    }

    public void RegisterUser(RegisterRequest registerRequest)
    {
        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(SignUp.this, SignIn.class));
                }
                else{
                    ShowDialogWindow("Что-то пошло не так");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void init(){
        firstName = findViewById(R.id.edFirstName);
        lastName = findViewById(R.id.edLastName);
        email = findViewById(R.id.edEmailSignUp);
        password = findViewById(R.id.edPasswordSignUp);
        repeatPassword = findViewById(R.id.edRepeatPassword);
        goToSignIn = findViewById(R.id.tvGoToSignIn);
        signUp = findViewById(R.id.btnSignUp);
    }

}
