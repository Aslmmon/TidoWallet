package com.test.utils.Common.custom_views

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R
import org.w3c.dom.Text

class CustomCarImage(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var header:TextView
    var image:ImageView
    var footer:TextView

    init {
        inflate(context, R.layout.custom_car_image, this)
        header = findViewById(R.id.header)
        image = findViewById(R.id.car_icon)
        footer = findViewById(R.id.footer)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.img_car)
         header.text = attributes.getString(R.styleable.img_car_header) ?:"header"
        footer.text = attributes.getString(R.styleable.img_car_footer) ?:"footer"



    }
}