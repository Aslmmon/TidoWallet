package com.tidow.tidowallet.features.main.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.allPermissionGranted
import com.tidow.tidowallet.custom.ACCOUNT_BALANCE
import com.tidow.tidowallet.custom.BALANCE_ACCOUNT
import com.tidow.tidowallet.custom.TRANSACTIONS_ACCOUNT
import com.tidow.tidowallet.databinding.FragmentQrCodeBinding
import com.tidow.tidowallet.model.BalanceAccount
import com.tidow.tidowallet.showSnackBar
import java.util.*


class QrCodeFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    lateinit var binding: FragmentQrCodeBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var transactionsDatabase: DatabaseReference


    val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    val PERMISSION_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQrCodeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirebase()
        initQrCodeScanner()
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                when (true) {
                    it.text.toLowerCase().startsWith("http:") -> Toast.makeText(requireContext(), "Scan result: url", Toast.LENGTH_LONG).show()
                    it.text.toLowerCase().startsWith("mailto:") -> Toast.makeText(
                        requireContext(),
                        "Scan result: mail",
                        Toast.LENGTH_LONG
                    ).show()

                    it.text.toLowerCase(Locale.ROOT).startsWith("tel:") -> Toast.makeText(requireContext(), "Scan result: telephone", Toast.LENGTH_LONG).show()

                    it.text.toLowerCase(Locale.ROOT).startsWith("geo:") ->
                        Toast.makeText(requireContext(), "Scan result: geo", Toast.LENGTH_LONG).show()

                    it.text.toLowerCase(Locale.ROOT).startsWith("WIFI:") ->
                        Toast.makeText(requireContext(), "Scan result: wifi", Toast.LENGTH_LONG).show()


                    else ->{
//                        Toast.makeText(requireContext(), "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//                        showDialogWithPrice(it.text)

                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(it.text))
                        startActivity(browserIntent)

                    }
                }

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        binding.btnScan.setOnClickListener {
            if (!requireContext().allPermissionGranted(REQUESTED_PERMISSIONS)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity?.requestPermissions(REQUESTED_PERMISSIONS, PERMISSION_CODE)
                }
            } else {
                codeScanner.startPreview()
            }
        }

    }

    private fun initQrCodeScanner() {
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
    }

    private fun showDialogWithPrice(text: String?) {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.buy_item_title))
            .setMessage(getString(R.string.buy_item)) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    // Continue with delete operation
                    val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

                    val accountBalance=preferences.getInt(ACCOUNT_BALANCE,0)
                    if (accountBalance != 0){
                        val amountToPay = text?.toInt()!!
                        if (amountToPay > accountBalance){
                            showErrorMessage()
                            return@OnClickListener
                        }
                        val totalCount = accountBalance  - amountToPay
                        database.setValue(BalanceAccount(totalCount))
                        transactionsDatabase.push().setValue(TransactionItem(pricePaid = amountToPay))
                    }else{
                        showErrorMessage()
                    }
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun showErrorMessage() {
        requireActivity().showSnackBar(
            binding.frame,
            resources.getString(R.string.not_allowed)
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (requireContext().allPermissionGranted(REQUESTED_PERMISSIONS)) {
                    codeScanner.startPreview()
                } else {
                    Toast.makeText(activity, "Permission Access Denied!", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference(firebaseAuth.uid!! + BALANCE_ACCOUNT)
        transactionsDatabase = FirebaseDatabase.getInstance().getReference(firebaseAuth.uid!! + TRANSACTIONS_ACCOUNT).child("transactins")

    }

    data class TransactionItem(var pricePaid:Int?=null)
}
