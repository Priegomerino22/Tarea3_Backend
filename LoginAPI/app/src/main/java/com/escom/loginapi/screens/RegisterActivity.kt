package com.escom.loginapi.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.escom.loginapi.model.AuthResponse
import com.escom.loginapi.model.RegisterRequest
import com.escom.loginapi.network.AuthService
import com.escom.loginapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authService = RetrofitClient
            .getRetrofit()
            .create(AuthService::class.java)

        setContent {

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(onClick = { finish() }) {
                        Text("Regresar")
                    }

                    Button(onClick = { recreate() }) {
                        Text("Recargar")
                    }

                }

                Spacer(modifier = Modifier.height(40.dp))

                Text("Registro", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {

                    val request = RegisterRequest(username, password)

                    authService.register(request)
                        .enqueue(object : Callback<AuthResponse> {

                            override fun onResponse(
                                call: Call<AuthResponse>,
                                response: Response<AuthResponse>
                            ) {

                                if (response.isSuccessful) {

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        response.body()?.message ?: "Registro exitoso",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "El usuario ya existe",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }

                            }

                            override fun onFailure(
                                call: Call<AuthResponse>,
                                t: Throwable
                            ) {

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Error de red",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        })

                }) {

                    Text("Registrar")

                }

            }

        }

    }

}