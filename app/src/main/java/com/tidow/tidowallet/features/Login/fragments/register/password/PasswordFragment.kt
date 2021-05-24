package com.tidow.tidowallet.features.Login.fragments.register.password

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.databinding.FragmentPasswordBinding
import com.tidow.tidowallet.features.main.MainActivity

class PasswordFragment : Fragment() {

    lateinit var binding: FragmentPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            val email = binding.customEmail.editTextDescription.text.toString()
            val password = binding.customPassword.editTextDescription.text.toString()

            val credential = EmailAuthProvider.getCredential(email, password)

            Firebase.auth.currentUser!!.linkWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.i("firebase", "linkWithCredential:success")
                        val user = task.result?.user
                        Log.i("firebase", "linkWithCredential:success ${user?.email}")

                        Navigation.goToMainActivity(requireContext())
                    } else {
                        Log.i("fireabase", "linkWithCredential:failure", task.exception)
                        Toast.makeText(
                            requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //  updateUI(null)
                    }
                }
        }
    }
}