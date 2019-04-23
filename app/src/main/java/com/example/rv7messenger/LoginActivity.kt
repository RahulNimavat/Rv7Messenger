package com.example.rv7messenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        login_btn.setOnClickListener {
            loggin_in()
        }

        back_to_register_tv.setOnClickListener {
            finish()
        }
    }

    fun loggin_in(){
        var usr_email_l = email_login_field.text.toString()
        var usr_pass_l = pass_login_field.text.toString()

        if (usr_email_l.isEmpty() || usr_pass_l.isEmpty()){
            Toast.makeText(this,"Please fill up all the fields",Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(usr_email_l,usr_pass_l)
            .addOnCompleteListener() {
                if(!it.isSuccessful) return@addOnCompleteListener
                Toast.makeText(this,"Welcome ${it.result.user}",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener(){
                Toast.makeText(this,"Login Failed ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
