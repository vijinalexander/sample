package com.example.stocktrading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)




        val regButton = findViewById<Button>(R.id.registerButton1)
        regButton.setOnClickListener {
            val email = findViewById<TextInputLayout>(R.id.registerEmail1).editText?.text
            val password = findViewById<TextInputLayout>(R.id.RegisterPassword1).editText?.text
            val user=UserData(email.toString(),password.toString())

            CoroutineScope(Dispatchers.IO).launch {

                val regApplication = application as LoginApplication
                val service = regApplication.registerService


                service.postRegData(user).enqueue(object :
                    Callback<registerDataClass?> {
                    override fun onFailure(call: Call<registerDataClass?>, t: Throwable) {
                        Toast.makeText(
                            this@registerActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<registerDataClass?>,
                        response: Response<registerDataClass?>
                    ) {
                        when {
                            response.code()==200 -> {

                                Toast.makeText(this@registerActivity, "Registration success!", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@registerActivity,LoginActivity::class.java)
                                startActivity(intent)

                            }
                            response.code()==500 -> {

                                Toast.makeText(
                                    this@registerActivity,
                                    "Registration failed!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                            else -> {
                                Toast.makeText(
                                    this@registerActivity,
                                    "User Already exists!!!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                })
            }
        }
    }
}





