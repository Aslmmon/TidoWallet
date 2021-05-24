package com.tidow.tidowallet.features.Login.fragments.register.verify_code

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.VERIFICATION_CODE
import com.tidow.tidowallet.databinding.FragmentVerifyCodeBinding
import org.koin.android.ext.android.inject


class VerifyCodeFragment : Fragment() {

    lateinit var binding: FragmentVerifyCodeBinding
    val sharedPrefrence: SharedPreferences by inject()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVerifyCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnVerifyCode.setOnClickListener {

            val codeEnterd = binding.pinCode.text.toString()
            Toast.makeText(requireContext(), codeEnterd, Toast.LENGTH_SHORT).show()


            val credential = PhoneAuthProvider.getCredential(sharedPrefrence.getString(VERIFICATION_CODE,"").toString(), codeEnterd)
            signInWithPhoneAuthCredential(credential)
            findNavController().navigate(R.id.goToPasswordFragment)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                  //  Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    Log.i("firebase",user.toString())
                } else {
                    // Sign in failed, display a message and update the UI
                 //   Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}