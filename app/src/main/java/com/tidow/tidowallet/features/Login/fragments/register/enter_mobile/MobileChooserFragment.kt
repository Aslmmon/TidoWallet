package com.tidow.tidowallet.features.Login.fragments.register.enter_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tidow.tidowallet.R
import com.tidow.tidowallet.databinding.FragmentMobileChooserBinding


class MobileChooserFragment : Fragment() {

    lateinit var binding: FragmentMobileChooserBinding

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
        requireActivity().findViewById<TextView>(R.id.tv_login).text = resources.getString(R.string.register_title)
        binding.btnVerify.setOnClickListener {
            findNavController().navigate(R.id.goToVerify)
        }
    }
}