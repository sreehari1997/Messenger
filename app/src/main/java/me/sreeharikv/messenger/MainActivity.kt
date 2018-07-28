package me.sreeharikv.messenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        register_button_register.setOnClickListener {

            val email = email_edittext_reg.text.toString()
            val password = password_edittext_reg.text.toString()
            val username = username_edittext_reg.text.toString()

            Log.d("MainActivity", "email is "+email)
            Log.d("MainActivity", "password  is "+password)
            Log.d("MainActivity", "username is "+username)

        }

        signintextview.setOnClickListener {
            Log.d("MainActivity", "Try to show the login page")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


    }
}
