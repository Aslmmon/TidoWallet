package com.tidow.tidowallet.features.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tidow.tidowallet.R
import com.tidow.tidowallet.features.Login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            delay(3000L)
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
        }
    }
}