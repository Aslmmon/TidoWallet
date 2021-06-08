package com.tidow.tidowallet

import android.content.Context
import android.content.res.Configuration
import android.view.View
import com.google.android.material.snackbar.Snackbar
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
