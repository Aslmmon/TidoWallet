package com.test.utils.Common.custom_views

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R
import com.test.utils.loadImage

class CustomImage(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val imageOriginal: ImageView
    val editIcon : ImageView

    init {
        inflate(context, R.layout.custom_image, this)
        imageOriginal = findViewById<ImageView>(R.id.iv_images_product)
        editIcon = findViewById<ImageView>(R.id.iv_delete)


    }

    fun setImage(resource:String){
        imageOriginal.loadImage(resource)
    }
}