package com.test.utils.Common.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.test.utils.R

@SuppressLint("UseCompatLoadingForDrawables")
class CustomTotal(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var image: ImageView
    var totalOrders: TextView
    var totalPayment: TextView

    init {
        inflate(context, R.layout.custom_total, this)
        image = findViewById(R.id.iv_icon)
        totalOrders = findViewById(R.id.tv_total_orders)
        totalPayment = findViewById(R.id.tv_price)


        val attributes = context.obtainStyledAttributes(attrs, R.styleable.custom_order_home)
        val isGreen = attributes.getBoolean(R.styleable.custom_order_home_isGreenOrder, false)
        if (isGreen) {
            findViewById<CardView>(R.id.card).backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorGreenWhitish)
            totalOrders.setTextColor(resources.getColor(R.color.colorGreen))
            totalPayment.setTextColor(resources.getColor(R.color.colorGreen))
            image.setImageDrawable(resources.getDrawable(R.drawable.tick_right))
        }
        totalOrders.text = attributes.getString(R.styleable.custom_order_home_android_text)

    }


}