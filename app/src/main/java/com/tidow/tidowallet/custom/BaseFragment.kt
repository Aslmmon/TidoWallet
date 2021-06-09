package com.tidow.tidowallet.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tidow.tidowallet.custom.progress.CustomProgress

open class BaseFragment : Fragment() {

    lateinit var loadingDialog: CustomProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = CustomProgress()

    }

    fun showProgress() {
        loadingDialog.show(requireContext(), "Loading ...")
    }

    fun dismissProgressDialog() {
        loadingDialog.dismissDialog()
    }
}