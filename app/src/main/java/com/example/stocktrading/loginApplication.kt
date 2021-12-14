package com.example.stocktrading

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.Type

class LoginApplication:Application() {
    lateinit var registerService:RegisterApiInterface
    lateinit var loginService: loginApiInterface
    override fun onCreate() {
        super.onCreate()
        loginService=loginApi()
        registerService=initHttpregisterApiService()
    }
    fun loginApi(): loginApiInterface
    {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/priceapp-secure-backend/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(loginApiInterface::class.java)
    }
    fun initHttpregisterApiService(): RegisterApiInterface {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/priceapp-secure-backend/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RegisterApiInterface::class.java)
    }
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter
                delegate.convert(it)
            }
        }
    }
}

