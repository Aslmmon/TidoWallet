package com.tidow.tidowallet.features.Login.fragments.register.enter_mobile

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.TOKEN
import com.tidow.tidowallet.custom.VERIFICATION_CODE
import com.tidow.tidowallet.databinding.FragmentMobileChooserBinding
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class MobileChooserFragment : Fragment() {

    lateinit var binding: FragmentMobileChooserBinding
    lateinit var firebaseAuth: FirebaseAuth

    val sharedPrefrenceEditor: SharedPreferences.Editor by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMobileChooserBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = Firebase.auth
        firebaseAuth.useAppLanguage()


        requireActivity().findViewById<TextView>(R.id.tv_login).text = resources.getString(R.string.register_title)

        binding.btnVerify.setOnClickListener {
            val mobileNumberEnterd = binding.customMobileNumber.editTextDescription.text
            if (mobileNumberEnterd.trim().length < 11){
                binding.customMobileNumber.editTextDescription.error = resources.getString(R.string.enter_mobile)
                return@setOnClickListener
            }
            binding.progres.visibility = View.VISIBLE

            val mobileNumber = "+2${mobileNumberEnterd}"

           // Toast.makeText(requireContext(), mobileNumber, Toast.LENGTH_SHORT).show()

            val options = createPhoneAuth(mobileNumber)
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }

    private fun createPhoneAuth(mobileNumber: String): PhoneAuthOptions {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(mobileNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity())                 // Activity (for callback binding)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phone: PhoneAuthCredential) {
                        Log.i("firebase", "onVerificationCompleted:$phone")
                    }

                    override fun onVerificationFailed(firebaseExcption: FirebaseException) {
                        Log.i("firebase", "onVerificationFailed:${firebaseExcption.message}")
                        if (firebaseExcption is FirebaseAuthInvalidCredentialsException) {
                            Log.i("firebase", "invalid Request")
                        } else if (firebaseExcption is FirebaseTooManyRequestsException) {
                            Log.i("firebase", "sms Quota exceeded")
                        }
                    }

                    override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, token)
                        binding.progres.visibility = View.GONE
                        sharedPrefrenceEditor.putString(VERIFICATION_CODE, verificationId).apply()
                        sharedPrefrenceEditor.putString(TOKEN, token.toString()).apply()
                        findNavController().navigate(R.id.goToVerify)
                    }
                }).build()
        return options
    }
}