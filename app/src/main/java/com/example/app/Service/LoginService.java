package com.example.app.Service;

import com.example.app.Request.LoginRequest;
import com.example.app.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);




}