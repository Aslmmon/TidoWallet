package com.tidow.tidowallet.features.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tidow.tidowallet.R
import com.tidow.tidowallet.features.Login.LoginActivity
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
            delay(3000L)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}