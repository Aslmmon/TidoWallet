package com.test.utils.Common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R

class  CustomEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var inputEdit: EditText
    var sendBtn: ImageView
    init {
        inflate(context, R.layout.custom_edit_text, this)
        inputEdit = findViewById(R.id.ed_message)
        sendBtn = findViewById(R.id.iv_send_message)

    }
    fun emptyInputText(){
        inputEdit.text.clear()
    }


}