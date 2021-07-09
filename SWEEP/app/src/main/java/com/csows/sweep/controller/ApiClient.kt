package com.csows.sweep.controller

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    companion object {
        private const val BASE_URL = "http://164.100.72.248/CeoDelhiOffice/api/"

         fun getClient(): APIInterface {

             val httpClient = OkHttpClient.Builder()
                 .callTimeout(2, TimeUnit.MINUTES)
                 .connectTimeout(20, TimeUnit.SECONDS)
                 .readTimeout(30, TimeUnit.SECONDS)
                 .writeTimeout(30, TimeUnit.SECONDS)

            var retrofit =  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build()

             val apiInterface : APIInterface = retrofit.create(APIInterface::class.java)

             return apiInterface
        }
        /*   private val retrofit = Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
               .build()

           val apiInterface : ApiInterface = retrofit.create(ApiInterface::class.java)*/
    }

}