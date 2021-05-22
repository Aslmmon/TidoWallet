package com.test.utils.Bases

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.utils.Common.CustomProgress
import org.koin.android.ext.android.inject

open class BaseActivity :AppCompatActivity(){

    lateinit var loadingDialog: CustomProgress
     val sharedPrefrenceEditor: SharedPreferences.Editor by inject()
     val sharedPrefrence: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = CustomProgress()

    }
    fun showProgress() {
        loadingDialog.show(this, "Loading ...")
    }

    fun getSharedPrefrenceEdit() = sharedPrefrenceEditor
    fun getSharedPrefrenceInstance() = sharedPrefrence

    fun dismissProgressDialog() {
        loadingDialog.dismissDialog()
    }


}