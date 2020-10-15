package com.morse.remote.core

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.morse.lib.core.RetryCallAdapterFactory
import com.morse.remote.common.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.omg.CORBA.Environment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private var retrofit: Retrofit?=null

    private fun getInstance () : Retrofit {
        if (retrofit == null) {
            var gson = GsonBuilder().disableHtmlEscaping()
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson.create()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addCallAdapterFactory(RetryCallAdapterFactory.create())
                    .client(getClientInstance())
                    .build()
        }

        return retrofit!!
    }

    public fun getNetworkInteractor () : MarvelApis {
        return getInstance()?.create(MarvelApis::class.java)
    }



    private fun getClientInstance () : OkHttpClient {

        var loggingInterceptor = HttpLoggingInterceptor()?.apply {
            setLevel( HttpLoggingInterceptor.Level.BODY )
        }
        var okHttp = OkHttpClient.Builder()?.apply {
            this.readTimeout(30 , TimeUnit.SECONDS)
            this.connectTimeout(30 , TimeUnit.SECONDS)
            this.writeTimeout(30, TimeUnit.SECONDS)
            this.addInterceptor(loggingInterceptor)

           // this.cache(getCache())
        }
        return okHttp?.build()
    }


}