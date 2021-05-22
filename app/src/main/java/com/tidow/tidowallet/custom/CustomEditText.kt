package com.tidow.tidowallet.custom

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tidow.tidowallet.R


class CustomTitleEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val editTextTile: TextView
    val editTextDescription : EditText

    init {
        inflate(context, R.layout.custom_title_edit, this)
        editTextTile = findViewById<TextView>(R.id.tv_title)
        editTextDescription = findViewById<EditText>(R.id.ed_data)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_Text)
        val title: String? = attributes.getString(R.styleable.edit_Text_android_text)
        val inputType: Int = attributes.getInt(R.styleable.edit_Text_android_inputType, EditorInfo.TYPE_NULL)
         val NAME_SPACE = "http://schemas.android.com/apk/res/android"
        val maxLength: Int = attrs.getAttributeIntValue(NAME_SPACE, "maxLength", 50)
        val isMandatory:Boolean = attributes.getBoolean(R.styleable.edit_Text_isMandatory, false)
        editTextTile.text = title ?: "Not Specified"
        if(isMandatory) editTextTile.append("*")
        inputType.let { editTextDescription.inputType = it }
        editTextDescription.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))


    }
}