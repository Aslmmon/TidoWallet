package com.tidow.tidowallet.features.main.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.*
import com.tidow.tidowallet.custom.VALUE_TO_BE_SEND
import com.tidow.tidowallet.databinding.FragmentMoneyBinding
import com.tidow.tidowallet.features.fawry.FawryPaymentActivity
import com.tidow.tidowallet.model.BalanceAccount
import com.tidow.tidowallet.showSnackBar


class MoneyFragment : Fragment() {
    lateinit var binding: FragmentMoneyBinding
    var firebaseuser = FirebaseAuth.getInstance()
     var balanceAmount:Int = 0
    var database = FirebaseDatabase.getInstance().getReference(firebaseuser.uid!! + BALANCE_ACCOUNT)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoneyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userNumber.text = firebaseuser.currentUser?.phoneNumber

        binding.btnWithDraw.setOnClickListener {
            binding.moneyGroup.visibility = View.VISIBLE
            //startActivity(Intent(requireContext(), FawryPaymentActivity::class.java))
        }
        binding.btnCheck.setOnClickListener {
            val amount = binding.customMoney.editTextDescription.text.toString()
            if (amount.toInt() < MAXIMUM_MONEY) {
                val intent = Intent(requireContext(),FawryPaymentActivity::class.java)
                intent.putExtra(VALUE_TO_BE_SEND ,amount)
                intent.putExtra(BALANCE_AMOUNT ,balanceAmount)

                startActivity(intent)
            }else requireActivity().showSnackBar(binding.constraint,resources.getString(R.string.maximum))
        }

    }

    override fun onResume() {
        super.onResume()
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(BalanceAccount::class.java)
                Log.e("firebase", value?.balanceMoney.toString())
                balanceAmount = value?.balanceMoney!!
                binding.tvBalance.text = value.balanceMoney.toString() + " " + value.currency
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message.toString())
            }

        })

    }
}