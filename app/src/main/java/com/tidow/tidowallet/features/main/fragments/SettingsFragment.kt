package com.tidow.tidowallet.features.main.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.*
import com.tidow.tidowallet.custom.SPLASH_TIMEOUT
import com.tidow.tidowallet.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    lateinit var firebase: FirebaseAuth
    lateinit var binding: FragmentSettingsBinding
    lateinit var languageChoosen: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignOut.setOnClickListener {
            firebase.signOut()
            Navigation.goToLoginActivity(requireContext())
        }

        binding.rdBtn.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.arabic -> languageChoosen = ARABIC
                R.id.english -> languageChoosen = ENGLISH
            }
        }

        binding.btnChangeLanguage.setOnClickListener {
            if (!::languageChoosen.isInitialized) {
                return@setOnClickListener
            } else {
                val editor: SharedPreferences.Editor =
                    activity?.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)?.edit()!!
                val isSAved = editor.putString(LANGUAGE, languageChoosen).commit()
                if (isSAved) Navigation.goToSplashActivity(requireContext())
            }

        }
    }
}