package me.sreeharikv.messenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        register_button_register.setOnClickListener {

            perfromaction()

        }

        already_have_account.setOnClickListener {
            Log.d("MainActivity", "Try to show the login page")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        photo_button.setOnClickListener {
            Log.d("MainActivity", "Try to show the image")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


    }

    var selectedphotouri : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 0 && data != null){
            //photo selected
            selectedphotouri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedphotouri)
            val bitmapDrawable = BitmapDrawable(bitmap)

            photo_button.setBackgroundDrawable(bitmapDrawable)

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
                    Log.d("MainActivity", "The user registered successfull with uid"+{it.result.user.uid})
                    uploadimagetofirebasestorage()


                }
                .addOnFailureListener {
                    Log.d("MainActivity", "Failed to register the user")
                    Toast.makeText(this, "Registration failed : ${it.message}", Toast.LENGTH_LONG).show()
                }
    }
    private fun uploadimagetofirebasestorage(){

        if(selectedphotouri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedphotouri!!)

                .addOnSuccessListener {
                    Log.d("MainActivity", "photo uploaded successfully")

                    ref.downloadUrl.addOnSuccessListener {
                        saveusertofirebasedatabase(it.toString())
                    }
                }


    }
    private fun saveusertofirebasedatabase(profileimageurl : String){

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid.toString(),username_edittext_reg.text.toString(),profileimageurl)
        ref.setValue(user)
        //save entire object

        Log.d("MainActivity","Successfully saved user to database")

    }
}

class User(val uid : String,val Username : String,val profileimageurl: String)
