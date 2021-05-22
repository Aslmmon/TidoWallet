package com.test.utils.Common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View.inflate
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.App.Companion.context
import com.test.utils.R


class CustomMoney (context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var inputEdit: EditText
    var sendBtn: ImageView
    init {
        inflate(context, R.layout.custom_money, this)
        inputEdit = findViewById(R.id.ed_message)
        sendBtn = findViewById(R.id.iv_send_message)

    }



}