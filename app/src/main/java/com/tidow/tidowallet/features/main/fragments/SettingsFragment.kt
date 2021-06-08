package com.tidow.tidowallet.features.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation
import com.tidow.tidowallet.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var firebase:FirebaseAuth
    lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignOut.setOnClickListener {
            firebase.signOut()
            Navigation.goToLoginActivity(requireContext())
        }
    }
}