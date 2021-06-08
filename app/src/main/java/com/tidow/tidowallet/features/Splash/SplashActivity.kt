package com.tidow.tidowallet.features.Splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.*
import com.tidow.tidowallet.setLocale
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val name = prefs.getString(LANGUAGE, ENGLISH) //"No name defined" is the default value.
        name?.let { setLocale(it) }

        GlobalScope.launch {
            delay(SPLASH_TIMEOUT)
            Navigation.goToLoginActivity(this@SplashActivity)
        }
    }
}