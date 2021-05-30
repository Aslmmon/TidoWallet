package com.tidow.tidowallet.features.Login.fragments.sign_in

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.databinding.FragmentSignInBinding


class SignInFragment : Fragment(), Validator.ValidationListener {

    lateinit var binding: FragmentSignInBinding
    lateinit var validator: Validator


    @NotEmpty(messageResId = R.string.email_not_empty)
    @Email
    private var emailEditText: EditText? = null

    @Password(min = 8,messageResId = R.string.password_not_less_than_6)
    @NotEmpty(messageResId = R.string.password_not_empty)
    private var passwordEditText: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        validator = Validator(this)
        validator.setValidationListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // requireActivity().findViewById<TextView>(R.id.tv_login).text = resources.getString(R.string.sign_in_title)
        emailEditText = binding.customEmail.editTextDescription
        passwordEditText = binding.customPassword.editTextDescription

        binding.customPassword.textInputLayout.hint = resources.getString(R.string.password_title)


        binding.btnSignIn.setOnClickListener {
            validator.validate()
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.goToChooseMobile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

    override fun onValidationSucceeded() {
        val email = binding.customEmail.editTextDescription.text.toString()
        val password = binding.customPassword.editTextDescription.text.toString()

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("firebase", "createUserWithEmail:success")
                    Navigation.goToMainActivity(requireContext())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view = error.view
            val message = error.getCollatedErrorMessage(requireContext())
            // Display error messages ;)
            if (view is EditText) {
                (view as EditText).error = message
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }
}