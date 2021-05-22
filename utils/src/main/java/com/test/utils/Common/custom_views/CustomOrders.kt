package com.test.utils.Common.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R
import com.test.utils.loadImage

@SuppressLint("ResourceType", "UseCompatLoadingForColorStateLists")
class CustomOrders(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {


    init {
        inflate(context, R.layout.custom_orders, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.custom_orders)
        val isGreen = attributes.getBoolean(R.styleable.custom_orders_isGreen,false)

        findViewById<TextView>(R.id.tv_orders).text = attributes.getString(R.styleable.custom_orders_order_title) ?: "not specifided"
        findViewById<TextView>(R.id.tv_price).text = attributes.getString(R.styleable.custom_orders_order_price) ?: "not specifided"
        findViewById<TextView>(R.id.tv_total_orders_title).text =  attributes.getString(R.styleable.custom_orders_order_orders)  ?: "not specifided"
        findViewById<TextView>(R.id.tv_salary).text =  attributes.getString(R.styleable.custom_orders_order_salary)  ?: "not specifided"
        findViewById<ImageView>(R.id.image_type_payment).setImageResource(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0))

        if(isGreen){
            findViewById<TextView>(R.id.tv_orders).setTextColor(resources.getColor(R.color.colorGreen))
            findViewById<TextView>(R.id.tv_price).setTextColor(resources.getColor(R.color.colorGreen))
            findViewById<TextView>(R.id.tv_price).backgroundTintList = resources.getColorStateList(R.color.colorGreenWhitish);
            findViewById<TextView>(R.id.tv_total_orders_title).setTextColor(resources.getColor(R.color.colorGreen))

        }
        //      logoTitle.text = title ?: "Not Specified"


    }


}