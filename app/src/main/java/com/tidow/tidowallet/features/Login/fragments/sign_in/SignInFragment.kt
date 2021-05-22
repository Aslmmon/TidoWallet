package com.tidow.tidowallet.features.Login.fragments.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tidow.tidowallet.R
import com.tidow.tidowallet.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<TextView>(R.id.tv_login).text = resources.getString(R.string.sign_in_title)

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.goToChooseMobile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}