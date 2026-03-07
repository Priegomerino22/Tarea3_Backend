package com.escom.loginapi.network

import com.escom.loginapi.model.ApiResponse
import com.escom.loginapi.model.AuthResponse
import com.escom.loginapi.model.LoginRequest
import com.escom.loginapi.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @GET("/")
    fun getHome(): Call<ApiResponse>

    @POST("/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

}