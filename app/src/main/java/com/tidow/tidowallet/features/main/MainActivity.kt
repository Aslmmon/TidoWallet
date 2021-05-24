package com.tidow.tidowallet.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<MaterialButton>(R.id.btn_signOut)
        btn.setOnClickListener {
            Firebase.auth.signOut()
            Navigation.goToLoginActivity(this)
        }
    }
}