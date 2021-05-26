package com.tidow.tidowallet.features.Login.fragments.register.password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment(), Validator.ValidationListener {

    lateinit var binding: FragmentPasswordBinding
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        validator = Validator(this)
        validator.setValidationListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = binding.customEmail.editTextDescription
        passwordEditText = binding.customPassword.editTextDescription

        binding.btnRegister.setOnClickListener {
            validator.validate()
        }
    }

    override fun onValidationSucceeded() {

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