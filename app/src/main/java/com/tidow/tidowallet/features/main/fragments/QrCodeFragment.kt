package com.tidow.tidowallet.features.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.tidow.tidowallet.R
import com.tidow.tidowallet.databinding.FragmentQrCodeBinding


class QrCodeFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    lateinit var binding:FragmentQrCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQrCodeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
//        codeScanner.decodeCallback = DecodeCallback {
//            requireActivity().runOnUiThread {
//                Toast.makeText(requireContext(), "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//            }
//        }
//        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
//            requireActivity().runOnUiThread {
//                Toast.makeText(requireContext(), "Camera initialization error: ${it.message}",
//                    Toast.LENGTH_LONG).show()
//            }
//        }

        binding.btnScan.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    override fun onPause() {
        super.onPause()
        Log.i("fragment","pause")
        codeScanner.releaseResources()
    }


    override fun onResume() {
        super.onResume()
        //codeScanner.startPreview()
    }


}