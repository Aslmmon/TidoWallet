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


        requireActivity().findViewById<TextView>(R.id.tv_login).text =
            resources.getString(R.string.register_title)

        binding.btnVerify.setOnClickListener {

//            val mobileNumber = binding.customMobileNumber.editTextDescription.text.toString()
            val mobileNumber = "+20${binding.customMobileNumber.editTextDescription.text}"

            Toast.makeText(requireContext(), mobileNumber, Toast.LENGTH_SHORT).show()

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(mobileNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity())                 // Activity (for callback binding)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phone: PhoneAuthCredential) {
                        Log.i("firebase", "onVerificationCompleted:$phone")
                        //  signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(firebaseExcption: FirebaseException) {
                        Log.i("firebase", "onVerificationFailed:${firebaseExcption.message}")
                        if (firebaseExcption is FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.i("firebase", "invalid Request")
                        } else if (firebaseExcption is FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            Log.i("firebase", "sms Quota exceeded")
                        }
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, token)
                        Log.i("firebase", "onCodeSent verificaion id :$verificationId")
                        Log.i("firebase", "onCodeSent token :$token")
                        sharedPrefrenceEditor.putString(VERIFICATION_CODE, verificationId).apply()
                        sharedPrefrenceEditor.putString(TOKEN, token.toString()).apply()

                        findNavController().navigate(R.id.goToVerify)
                    }
                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }
}