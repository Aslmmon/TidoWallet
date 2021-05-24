package com.tidow.tidowallet.features.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tidow.tidowallet.R
import com.tidow.tidowallet.custom.Navigation

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null){
            Navigation.goToMainActivity(this)
        }
    }
}