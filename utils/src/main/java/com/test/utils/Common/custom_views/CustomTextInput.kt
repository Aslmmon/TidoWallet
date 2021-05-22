package com.test.utils.Common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.test.utils.R

class CustomTextInput(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
     var editText : TextInputEditText
     var inputLayotu:TextInputLayout

    init {
        inflate(context, R.layout.custom_text_input, this)
        editText = findViewById<TextInputEditText>(R.id.et_notes)
        inputLayotu = findViewById(R.id.notes_et)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_Text)
        val title: String? = attributes.getString(R.styleable.edit_Text_android_hint)
        inputLayotu.hint = title


   //     val attributes = context.obtainStyledAttributes(attrs, R.styleable.custom_complain)
     //   val isFromFloria: Boolean = attributes.getBoolean(R.styleable.custom_complain_fromFloria, false)



    }


}