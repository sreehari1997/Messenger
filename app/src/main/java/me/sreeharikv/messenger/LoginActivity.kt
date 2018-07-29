package me.sreeharikv.messenger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            performsignin()
        }


        register_textview_login.setOnClickListener {
            Log.d("LoginActivity", "Try to show the Register page")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun performsignin(){

            val un = username_textview_login.text.toString()
            val pw = password_textview_login.text.toString()

            Log.d("MainActivity", "email is "+un)
            Log.d("MainActivity", "password  is "+pw)
        if(un.isEmpty() || pw.isEmpty()) {
            Toast.makeText(this, "please enter the credentials", Toast.LENGTH_LONG).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(un,pw)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    // else if
                    Log.d("Main", "The user sign successfull with uid"+{it.result.user.uid})

                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to login the user")
                    Toast.makeText(this, "login failed : ${it.message}", Toast.LENGTH_LONG).show()
                }

    }
}