package com.escom.loginapi.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    .background(Color(0xFFF3E8FF))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = { finish() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9575CD)
                        )
                    ) {
                        Text("Regresar", color = Color.White)
                    }

                    Button(
                        onClick = { recreate() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB39DDB)
                        )
                    ) {
                        Text("Recargar", color = Color.White)
                    }

                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF5B2C87)
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFFB39DDB),
                        focusedLabelColor = Color(0xFF7E57C2),
                        cursorColor = Color(0xFF7E57C2)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFFB39DDB),
                        focusedLabelColor = Color(0xFF7E57C2),
                        cursorColor = Color(0xFF7E57C2)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

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

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7E57C2)
                    )
                ) {

                    Text("Iniciar sesión", color = Color.White)

                }

            }

        }

    }

}