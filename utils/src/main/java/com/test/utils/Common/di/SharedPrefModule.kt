package com.test.utils.Common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPref = module {

    single{
        getSharedPrefrences(androidContext() as Application)
    }

    single<SharedPreferences.Editor> {
        getSharedPrefrences(androidContext()).edit()
    }

}

fun getSharedPrefrences(androidApplication: Context): SharedPreferences {
    return androidApplication.getSharedPreferences("app", Context.MODE_PRIVATE)
}