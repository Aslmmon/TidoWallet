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
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.BALANCE_ACCOUNT
import com.tidow.tidowallet.custom.EGP
import com.tidow.tidowallet.custom.VERIFICATION_CODE
import com.tidow.tidowallet.databinding.FragmentVerifyCodeBinding
import com.tidow.tidowallet.model.BalanceAccount
import org.koin.android.ext.android.inject


class VerifyCodeFragment : Fragment() {

    lateinit var binding: FragmentVerifyCodeBinding
    val sharedPrefrence: SharedPreferences by inject()
    val database = FirebaseDatabase.getInstance()


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
            if (codeEnterd.trim().length < 6) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.enter_valid_Code),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val credential = PhoneAuthProvider.getCredential(
                sharedPrefrence.getString(VERIFICATION_CODE, "").toString(), codeEnterd
            )
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("firebase", "signInWithCredential:success")
                    val user = task.result?.user
                    addDataToFirebase(user)
                    findNavController().navigate(R.id.goToPasswordFragment)
                    Log.i("firebase", user.toString())
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.i("firebase", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                    // Update UI
                }
            }
    }

    private fun addDataToFirebase(user: FirebaseUser?) {
        database.getReference(user?.uid!! + BALANCE_ACCOUNT).setValue(BalanceAccount())
            .addOnSuccessListener {

            }.addOnFailureListener { failuer ->
            Log.e("error", failuer.message.toString())
        }
    }
}

