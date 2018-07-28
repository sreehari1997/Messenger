package me.sreeharikv.messenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        register_button_register.setOnClickListener {

            perfromaction()

        }

        signintextview.setOnClickListener {
            Log.d("MainActivity", "Try to show the login page")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


    }

    private fun perfromaction(){
        val email = email_edittext_reg.text.toString()
        val password = password_edittext_reg.text.toString()
        val username = username_edittext_reg.text.toString()

        Log.d("MainActivity", "email is "+email)
        Log.d("MainActivity", "password  is "+password)
        Log.d("MainActivity", "username is "+username)

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "please enter the credentials", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    // else if
                    Log.d("Main", "The user registered successfull with uid"+{it.result.user.uid})

                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to register the user")
                    Toast.makeText(this, "Registration failed : ${it.message}", Toast.LENGTH_LONG).show()
                }
    }
}
