package com.test.utils.Common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.test.utils.R

class CustomProgress() {

    private var dialog: CustomDialog? = null

    fun show(context: Context): Dialog {
        return show(context, null)
    }


    fun show(context: Context, title: CharSequence?): Dialog {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.custom_dialog_new, null)
        if (title != null) {
            view.findViewById<TextView>(R.id.cp_title).text = title
        }
        view.findViewById<CardView>(R.id.cp_cardview).setCardBackgroundColor(Color.parseColor("#70000000"))
        dialog = CustomDialog(context)
        dialog?.setContentView(view)
        dialog?.setCancelable(false)
        if(!dialog?.isShowing!!) dialog?.show()

        return dialog!!
    }

    fun dismissDialog() {
        if (dialog?.isShowing == true && dialog != null) dialog?.dismiss()
    }

    fun isShowingDialog(): Boolean? {
        return dialog?.isShowing
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(android.R.color.transparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }

        }
    }
}