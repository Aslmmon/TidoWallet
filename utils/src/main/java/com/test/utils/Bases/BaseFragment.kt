package com.test.utils.Bases

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.utils.ACCESS_TOKEN
import com.test.utils.Common.CustomProgress
import com.test.utils.LOGIN_CLASS_NAME
import com.test.utils.showAlertDialog
import org.koin.android.ext.android.inject


open class BaseFragment : Fragment() {
    lateinit var loadingDialog: CustomProgress
    var viewNeededToBeHidden: View? = null

    val sharedPrefrenceEditor: SharedPreferences.Editor by inject()
    val sharedPrefrence: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = CustomProgress()

    }

    fun showProgress(viewToBeHidden: View? = null) {

        loadingDialog.show(requireContext(), "Loading ...")
        Log.i("progress", "called once")
        if (viewToBeHidden != null) viewNeededToBeHidden = viewToBeHidden
    }

    fun dismissProgressDialog() {
        loadingDialog.dismissDialog()
        if (viewNeededToBeHidden != null) viewNeededToBeHidden?.visibility = View.VISIBLE
    }

    fun getSharedPrefrenceEdit() = sharedPrefrenceEditor
    fun getSharedPrefrenceInstance() = sharedPrefrence

    fun goToLogin() {

        requireActivity().showAlertDialog(resources.getString(com.test.utils.R.string.log_out),resources.getString(com.test.utils.R.string.are_you_sure),launchFunction = {
            val intent = Intent(requireContext(), Class.forName(LOGIN_CLASS_NAME))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            sharedPrefrenceEditor.remove(ACCESS_TOKEN).apply()
            requireActivity().finish()
        })


    }


}

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
/**
 * BaseFragment with viewBinding
 */
//
//abstract class BaseFragment<VB: ViewBinding>(
//        private val inflate: Inflate<VB>
//) : Fragment() {
//
//    private var _binding: VB? = null
//    val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        _binding = inflate.invoke(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

/**
 * Usage
 */
//class HomeFragment() : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.homeText.text = "Hello view binding"
//    }
//}