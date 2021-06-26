package com.tidow.tidowallet.features.main.fragments

import android.annotation.SuppressLint
import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.*
import com.tidow.tidowallet.databinding.FragmentMoneyBinding
import com.tidow.tidowallet.features.fawry.FawryPaymentActivity
import com.tidow.tidowallet.features.main.fragments.adapter.TransactionItemAdapter
import com.tidow.tidowallet.model.BalanceAccount
import com.tidow.tidowallet.showSnackBar
import com.tidow.tidowallet.startAnimation


class MoneyFragment : BaseFragment() {
    lateinit var binding: FragmentMoneyBinding
    var firebaseuser = FirebaseAuth.getInstance()
    var balanceAmount: Int = 0
    var transactionItemAdapter = TransactionItemAdapter()
    var database = FirebaseDatabase.getInstance().getReference(firebaseuser.uid!! + BALANCE_ACCOUNT)
    var transactionsDb =
        FirebaseDatabase.getInstance().getReference(firebaseuser.uid!! + TRANSACTIONS_ACCOUNT)
            .child("transactins")

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
        showProgress()
        binding.rvTransactions.adapter = transactionItemAdapter

        if (firebaseuser.currentUser?.email.isNullOrEmpty()) binding.userNumber.text =
            firebaseuser.currentUser?.phoneNumber
        else binding.userNumber.text =
            firebaseuser.currentUser?.email ?: firebaseuser.currentUser?.phoneNumber
        Log.i("user", firebaseuser.currentUser?.email.toString())
        Log.i("user", firebaseuser.currentUser?.phoneNumber.toString())

        binding.btnWithDraw.setOnClickListener {
            if (checkForBalance()) return@setOnClickListener
            binding.moneyGroup.visibility = View.VISIBLE
        }
        binding.btnCheck.setOnClickListener {
            val amount = binding.customMoney.editTextDescription.text.toString()
            if (checkForBalance()) return@setOnClickListener
            if (amount.toInt() + balanceAmount <= MAXIMUM_MONEY) {
                val intent = Intent(requireContext(), FawryPaymentActivity::class.java)
                intent.putExtra(VALUE_TO_BE_SEND, amount)
                intent.putExtra(BALANCE_AMOUNT, balanceAmount)
                startActivity(intent)
            } else showSnackbar()
        }

    }

    private fun checkForBalance(): Boolean {
        if (balanceAmount >= MAXIMUM_MONEY) {
            showSnackbar()
            return true
        }
        return false
    }

    private fun MoneyFragment.showSnackbar() {
        requireActivity().showSnackBar(
            binding.constraint,
            resources.getString(R.string.maximum)
        )
    }

    override fun onResume() {
        super.onResume()
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(BalanceAccount::class.java)
                Log.e("firebase", value?.balanceMoney.toString())
                dismissProgressDialog()
                value?.balanceMoney.let { balanceAmount = it!! }
                if (context != null) {
                    val preferences =
                        PreferenceManager.getDefaultSharedPreferences(requireActivity())
                    val editor = preferences.edit()
                    editor.putInt(ACCOUNT_BALANCE, balanceAmount)
                    editor.apply()
                    requireContext().startAnimation(binding.tvBalance, value!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dismissProgressDialog()
                Log.e("firebase", error.message.toString())
            }

        })


        transactionsDb.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val values = mutableListOf<QrCodeFragment.TransactionItem>()
                if (context != null) {
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        val value =
                            dataSnapshot.getValue(QrCodeFragment.TransactionItem::class.java)
                        value?.let { values.add(it) }
                        Log.i("data", value?.pricePaid.toString())
                    }
                    if (values.isEmpty()) showEmptyView()
                    else {
                        removeEmptyView()
                        transactionItemAdapter.submitList(values)
                    }
                }
            }

            private fun showEmptyView() {
                binding.emptyGroup.visibility = View.VISIBLE
            }

            private fun removeEmptyView() {
                binding.emptyGroup.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message.toString())
            }

        })


    }
}