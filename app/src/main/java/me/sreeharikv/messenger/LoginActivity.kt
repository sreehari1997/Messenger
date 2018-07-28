package me.sreeharikv.messenger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val un = username_textview_login.text.toString()
            val pw = password_textview_login.text.toString()

            Log.d("MainActivity", "email is "+un)
            Log.d("MainActivity", "password  is "+pw)
        }

        register_textview_login.setOnClickListener {
            Log.d("LoginActivity", "Try to show the Register page")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}