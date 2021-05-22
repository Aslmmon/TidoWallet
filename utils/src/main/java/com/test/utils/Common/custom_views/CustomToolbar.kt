package com.test.utils.Common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R

class CustomToolbar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val logoTitle: TextView

    init {
        inflate(context, R.layout.custom_toolbar, this)
        logoTitle = findViewById<TextView>(R.id.tv_logo_title)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.logo_title)
        val title: String? = attributes.getString(R.styleable.logo_title_android_text)
        logoTitle.text = title ?: "Not Specified"


    }
}