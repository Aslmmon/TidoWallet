package com.tidow.tidowallet.features.fawry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emeint.android.fawryplugin.Plugininterfacing.FawrySdk
import com.emeint.android.fawryplugin.Plugininterfacing.PayableItem
import com.emeint.android.fawryplugin.exceptions.FawryException
import com.emeint.android.fawryplugin.interfaces.FawrySdkCallback
import com.emeint.android.fawryplugin.managers.FawryPluginAppClass
import com.emeint.android.fawryplugin.models.BillItem
import com.google.firebase.auth.FirebaseAuth
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.ALpha
import com.tidow.tidowallet.custom.VALUE_TO_BE_SEND
import java.util.*


class FawryPaymentActivity : AppCompatActivity(R.layout.fawry_layout) {


    //var merchantRefNumber: String = "OOXGVI9E59RPT2EX"
    var merchantRefNum: String = randomAlphaNumeric(16)

    var payableItem: PayableItem? = null
    var items: List<PayableItem>? = null
    var isOrderSubmittied: Boolean = false
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FawrySdk.init(FawrySdk.Styles.STYLE1);
        firebaseAuth = FirebaseAuth.getInstance()

        val intent = intent.extras
        val data = intent?.getString(VALUE_TO_BE_SEND)
        payableItem = BillItem(merchantRefNum,merchantRefNum, "1", data)
        items = ArrayList()
        (items as ArrayList<PayableItem>).add(payableItem as BillItem)
        FawryPluginAppClass.skipCustomerInput = true
        FawryPluginAppClass.username = firebaseAuth.currentUser?.phoneNumber ?: "01094105667"
        FawryPluginAppClass.email = firebaseAuth.currentUser?.email ?: "aslmmon993@gmail.com"

        initializeFawry()
        FawrySdk.startPaymentActivity(this)
    }

    private fun initializeFawry() {


        try {

            FawrySdk.initialize(this, "https://atfawry.fawrystaging.com",
                    object : FawrySdkCallback {
                        override fun paymentOperationSuccess(trxID: String, customParams: Any) {
                            if (customParams is Bundle) {
                                customParams.keySet().forEach {
                                    if (it == "request_result") {
                                        val data = customParams.get(it)
                                        Log.i("fawry", data.toString())

                                        if (data == 200) {
                                            isOrderSubmittied = true
                                        }

                                    }
                                }
                            }
                        }

                        override fun paymentOperationFailure(errorMessage: String, customParams: Any) {
                            Log.i("fawryEx", errorMessage.toString())
                        }
                    },"1tSa6uxz2nTDgOBrY4QrFg==", merchantRefNum, items,
                FawrySdk.Language.EN, //FawrySdk.Language.EN /*use FawrySdk.Language.AR for Arabic*/,
                    PAYMENT_REQUEST_ID /*for activity Result*/, null, UUID(1, 2))
        } catch (e: FawryException) {
            Log.i("fawryEx", e.message.toString())
            Log.i("fawryEx", e.printStackTrace().toString())
        }
    }

    companion object {
        const val PAYMENT_REQUEST_ID = 201
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK || requestCode == PAYMENT_REQUEST_ID) {
            if (isOrderSubmittied) {
                finish()
            }else{
                finish()
            }
        }
    }
    fun randomAlphaNumeric(count: Int): String {
        var countne = count
        val builder = StringBuilder()
        while (countne-- != 0) {
            val character = (Math.random() * ALpha.length).toInt()
            builder.append(ALpha[character])
        }
        return builder.toString()
    }
}