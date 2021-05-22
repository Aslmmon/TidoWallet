package com.test.utils.Common.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.test.utils.NAME_SPACE
import com.test.utils.R

class CustomButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val btnTitle: TextView
    val btnIcon: ImageView


    init {
        inflate(context, R.layout.custom_button, this)
        btnTitle = findViewById<TextView>(R.id.tv_btn_title)
        btnIcon = findViewById(R.id.btn_icon)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.btn_custom)
        val title: String? = attributes.getString(R.styleable.btn_custom_android_text)
        val src_resource = attrs.getAttributeResourceValue(NAME_SPACE, "src", 0)
        src_resource.let {
            btnIcon.setImageResource(src_resource)
            //btnIcon.background.setTint(ContextCompat.getColor(context, R.color.colorPrimary))

        }
        val isGray = attributes.getBoolean(R.styleable.btn_custom_isGray, false)
        if (isGray) {
            findViewById<FrameLayout>(R.id.frame).background = ContextCompat.getDrawable(context,R.drawable.bg_colored_gray)
            btnTitle.setTextColor(ContextCompat.getColor(context,R.color.colorgray))
            btnIcon.clearColorFilter()

        }else{
            btnIcon.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary));

        }

        btnTitle.text = title ?: resources.getString(R.string.add_product_title)


    }
}