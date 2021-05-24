package com.tidow.tidowallet.features.Splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.custom.SPLASH_TIMEOUT
import com.tidow.tidowallet.setLocale
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(Locale.getDefault().language)

        GlobalScope.launch {
            delay(SPLASH_TIMEOUT)
            Navigation.goToLoginActivity(this@SplashActivity)
        }
    }
}