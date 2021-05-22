package com.tidow.tidowallet.features.Login.fragments.register.verify_code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tidow.tidowallet.R
import com.tidow.tidowallet.databinding.FragmentVerifyCodeBinding


class VerifyCodeFragment : Fragment() {

    lateinit var binding: FragmentVerifyCodeBinding


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
            findNavController().navigate(R.id.goToPasswordFragment)
        }
    }
}