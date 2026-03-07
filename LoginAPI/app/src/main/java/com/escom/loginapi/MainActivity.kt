package com.escom.loginapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.escom.loginapi.model.ApiResponse
import com.escom.loginapi.network.AuthService
import com.escom.loginapi.network.RetrofitClient
import com.escom.loginapi.screens.LoginActivity
import com.escom.loginapi.screens.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitClient
            .getRetrofit()
            .create(AuthService::class.java)

        setContent {

            var message by remember { mutableStateOf("Cargando...") }

            LaunchedEffect(Unit) {

                apiService.getHome().enqueue(object : Callback<ApiResponse> {

                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {

                        if (response.isSuccessful) {
                            message = response.body()?.message ?: "Sin respuesta"
                        }

                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        message = "Error de conexión"
                    }

                })

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(message)

                Spacer(modifier = Modifier.height(40.dp))

                Button(onClick = {

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)

                }) {
                    Text("Iniciar sesión")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {

                    val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                    startActivity(intent)

                }) {
                    Text("Registrarse")
                }

            }

        }

    }

}