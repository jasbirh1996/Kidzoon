package com.htf.kidzoon.netUtils

import com.htf.htfnew.utils.AppPreferences

import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.Constants
import com.htf.kidzoon.utils.MyApplication
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppClient {


    fun getClient(): APIInterface {
        val logging = HttpLoggingInterceptor()
        if (AppUtils.isAppDebug) {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder()
                    .header("X-Requested-With", "XMLHttpRequest")

                val userToken =
                    AppPreferences.getInstance(MyApplication.getInstance()).getFromPreference(
                        Constants.KEY_TOKEN
                    )
                if (userToken != null) {
                    if (userToken.length > 5) {
                        builder.header("Authorization", "Bearer $userToken")
                    }
                } else if (AppSession.userToken.length > 5) {
                    builder.header("Authorization", "Bearer " + AppSession.userToken)
                }

                val newRequest = builder.build()
                chain.proceed(newRequest)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(APIInterface::class.java)
    }


}