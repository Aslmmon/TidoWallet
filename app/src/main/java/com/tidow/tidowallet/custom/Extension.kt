package com.tidow.tidowallet

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.tidow.tidowallet.model.BalanceAccount
import java.util.*


fun Context?.setLocale(langugeNeeded: String) {
    val locale = Locale(langugeNeeded)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    this?.resources?.updateConfiguration(
            config,
            this.resources.displayMetrics
    )
}
fun Context.showSnackBar(view :View ,message:String){
    Snackbar.make(view,message, Snackbar.LENGTH_SHORT).show()
}
@SuppressLint("SetTextI18n")
fun Context.startAnimation(textView: TextView, balanceAccount: BalanceAccount) {
    val animator = ValueAnimator.ofInt(0, balanceAccount.balanceMoney)
    animator.duration = 1000 // 5 seconds
    animator.addUpdateListener { animation ->
        textView.text = animation.animatedValue.toString() + " " + balanceAccount.currency
    }
    animator.start()
}
