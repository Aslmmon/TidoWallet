package com.tidow.tidowallet.features.Login.fragments.sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<TextView>(R.id.tv_login).text = resources.getString(R.string.sign_in_title)


        binding.btnSignIn.setOnClickListener {
            val email = binding.customEmail.editTextDescription.text.toString()
            val password = binding.customPassword.editTextDescription.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("firebase", "createUserWithEmail:success")
                    Navigation.goToMainActivity(requireContext())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.goToChooseMobile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}