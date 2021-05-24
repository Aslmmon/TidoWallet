package com.tidow.tidowallet

import android.content.Context
import android.content.res.Configuration
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
