package com.test.utils.Common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import com.test.utils.R

class CustomComplainDetail(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {



    init {
        inflate(context, R.layout.custom_complain, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.custom_complain)
        val isFromFloria: Boolean = attributes.getBoolean(R.styleable.custom_complain_fromFloria, false)
        if (isFromFloria) {
            findViewById<Group>(R.id.floria_user_group_).visibility = View.VISIBLE
            findViewById<Group>(R.id.floria_complain_group_).visibility = View.INVISIBLE
        } else {
            findViewById<Group>(R.id.floria_user_group_).visibility = View.GONE
            findViewById<Group>(R.id.floria_complain_group_).visibility = View.VISIBLE
        }


    }


}