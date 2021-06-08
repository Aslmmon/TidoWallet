package com.tidow.tidowallet.custom

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.tidow.tidowallet.features.Login.LoginActivity
import com.tidow.tidowallet.features.Splash.SplashActivity
import com.tidow.tidowallet.features.main.MainActivity

object Navigation {

    fun goToMainActivity(ctx: Context) {
        val intent = Intent(ctx, MainActivity::class.java)
        (ctx as Activity).startActivity(intent)
        ctx.finish()
    }
    fun goToLoginActivity(ctx: Context) {
        val intent = Intent(ctx, LoginActivity::class.java)
        (ctx as Activity).startActivity(intent)
        ctx.finish()
    }

    fun goToSplashActivity(ctx: Context) {
        val intent = Intent(ctx, SplashActivity::class.java)
        (ctx as Activity).startActivity(intent)
        ctx.finish()
    }

}