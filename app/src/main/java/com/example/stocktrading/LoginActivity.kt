package com.example.stocktrading

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val regButton = findViewById<Button>(R.id.RegisterButton)
        val loginButton=findViewById<Button>(R.id.registerButton1)



        regButton.setOnClickListener {

            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    loading.isDismiss()
                }
            }, 3000)
            val regScreenIntent = Intent(this, registerActivity::class.java)

            startActivity(regScreenIntent)

        }

        loginButton.setOnClickListener {

            val email = findViewById<TextInputLayout>(R.id.registerEmail1).editText?.text
            val password = findViewById<TextInputLayout>(R.id.RegisterPassword1).editText?.text

            val user=UserData(email.toString(),password.toString())


            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as LoginApplication
                val service=sampleApplication.loginService
                service.postData(user).enqueue(object : Callback<loginDataClass?> {
                    override fun onResponse(call: Call<loginDataClass?>, response: Response<loginDataClass?>) {
                        if(response.isSuccessful)
                        {
                            val intent = Intent(this@LoginActivity,DrawnActivity::class.java)
                            startActivity(intent)
                            val sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)
                            val editor=sharedPreferences.edit()
                            editor.apply()
                            {
                                putString("token",response.body()!!.token)
                            }.apply()

                        }
                        else
                        {
                        }
                    }

                    override fun onFailure(call: Call<loginDataClass?>, t: Throwable) {

                    }
                })
            }
        }
    }
}