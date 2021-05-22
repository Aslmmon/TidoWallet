package com.test.utils.Common.network

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.floriaapp.core.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.utils.ACCESS_TOKEN
import com.test.utils.App
import com.test.utils.Common.di.getSharedPrefrences
import com.test.utils.LANGUAGE_PREFRENCE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Aslm on 1/1/2020
 */

//private val sLogLevel = HttpLoggingInterceptor.Level.BODY

private const val baseUrl = "https://staging.floria-app.com"


val loggingInterceptor = LoggingInterceptor.Builder()
        .loggable(BuildConfig.DEBUG)
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request("Request")
        .response("Response")
        .build()

//private fun getLogInterceptor() = HttpLoggingInterceptor().apply { level = sLogLevel }

fun createNetworkClient() =
        retrofitClient(baseUrl)

private fun okHttpClient2() = OkHttpClient.Builder()
        .addInterceptor(headersInterceptor())
        .addInterceptor(loggingInterceptor).build()

private fun retrofitClient(baseUrl: String): Retrofit =
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient2())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()


fun headersInterceptor() = Interceptor { chain ->

    val token = getSharedPrefrences(androidApplication = App.getAppContext()).getString(ACCESS_TOKEN, "")
    val language = getSharedPrefrences(androidApplication = App.getAppContext()).getString(LANGUAGE_PREFRENCE, "en")

    Log.i("language","lanugage in app is $language")

    chain.proceed(
            chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .addHeader("Accept-Language", language.toString())
                    .build())
}
//fun headersInterceptor() = Interceptor { chain ->
//    chain.proceed(
//            chain.request().newBuilder()
//                    .addHeader("Accept", "application/json")
//                    .addHeader("Accept-Language",SaveSharedPreference.getLanguage(App.getAppContext())!!)
//                    .addHeader("Authorization", "Bearer ${SaveSharedPreference.getBackendUserToken(App.getAppContext())}")
//                    .build()
//    )
//}

private fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
        okHttpClientBuilder.apply {
            readTimeout(30L, TimeUnit.SECONDS)
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)

        }

class SharedPrefrencesWrapper(private var sharedPrefrence: SharedPreferences) {

    fun saveString(key: String, value: String) {
        sharedPrefrence.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        return sharedPrefrence.getString(key, defValue)!!
    }

}