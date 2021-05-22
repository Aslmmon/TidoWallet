package com.test.utils.Common.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R
import com.test.utils.loadImage
import org.w3c.dom.Text

class CustomTextWithTitle(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var cost: TextView
    var orderTitle: TextView

    init {
        inflate(context, R.layout.custom_text_with_title, this)
        cost = findViewById<TextView>(R.id.price)
        orderTitle = findViewById(R.id.order_title)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.order_custom)
        orderTitle.text = attributes.getString(R.styleable.order_custom_android_text)
        val isGreen = attributes.getBoolean(R.styleable.order_custom_isGreenColored, false)
        if (isGreen) {
            cost.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorGreenWhitish));
            cost.setTextColor(resources.getColor(R.color.colorGreen))


        }


    }

}