package com.test.utils.Ext

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import java.util.*

fun Context.createSpinner(list: ArrayList<String>, fromTags: Boolean = false): ArrayAdapter<String?> {
    val spinnerAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(this, R.layout.simple_spinner_item, list as List<String?>) {
        override fun getCount(): Int {
            val newList = mutableListOf<String>()
            list.forEachIndexed { index, s ->
                if (index != 0) newList.add(s)
            }
            return if (fromTags) newList.size else list.size // Truncate the list
        }
    }

//    val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
//            R.layout.simple_spinner_item, list)

    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return spinnerAdapter
}
