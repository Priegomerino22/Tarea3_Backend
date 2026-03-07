package com.escom.loginapi.screens

import android.content.Intent
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
import com.escom.loginapi.model.LoginRequest
import com.escom.loginapi.network.AuthService
import com.escom.loginapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {

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

                Text("Login", style = MaterialTheme.typography.headlineMedium)

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

                    val request = LoginRequest(username, password)

                    authService.login(request)
                        .enqueue(object : Callback<AuthResponse> {

                            override fun onResponse(
                                call: Call<AuthResponse>,
                                response: Response<AuthResponse>
                            ) {

                                if (response.isSuccessful) {

                                    val intent = Intent(
                                        this@LoginActivity,
                                        WelcomeActivity::class.java
                                    )

                                    intent.putExtra(
                                        "username",
                                        response.body()?.username
                                    )

                                    startActivity(intent)

                                } else {

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Credenciales incorrectas",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }

                            }

                            override fun onFailure(
                                call: Call<AuthResponse>,
                                t: Throwable
                            ) {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Error de conexión",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        })

                }) {

                    Text("Iniciar sesión")

                }

            }

        }

    }

}