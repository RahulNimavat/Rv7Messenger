package com.example.rv7messenger

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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_up_btn.setOnClickListener {
            //var usr_name_s = usr_name_field.text.toString()
            signing_up()
        }

        have_account.setOnClickListener {
            var haveaccIntent = Intent(this,LoginActivity::class.java)
            startActivity(haveaccIntent)
        }

        pic_select_btn.setOnClickListener {
            var picIntent = Intent(Intent.ACTION_PICK)
            picIntent.type = "image/*"
            startActivityForResult(picIntent,0)
        }
    }

    var selected_pic: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("pic select","Selected")

            selected_pic = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selected_pic)
           circle_img_v.setImageBitmap(bitmap)
            pic_select_btn.alpha = 0f
        }
    }



    fun signing_up(){

        var usr_email_s = email_field.text.toString()
        var usr_pass_s = pass_field.text.toString()

        if(usr_email_s.isEmpty() || usr_pass_s.isEmpty()){
            Toast.makeText(this,"No Field can be empty",Toast.LENGTH_SHORT).show()
            return
        }

        //firebase user creation
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usr_email_s,usr_pass_s)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener

                uploadPicToFirebase()
                Log.d("USER Creation","is Successfull with ${it.result.user.uid}")

            }
            .addOnFailureListener {
            Toast.makeText(this,"failure because ${it.message}",Toast.LENGTH_SHORT).show()
            Log.d("USER Creation","is failed with ${it.message}")
        }
    }


    fun uploadPicToFirebase(){

        if(selected_pic == null) return

        val f_pname = UUID.randomUUID().toString()
        val f_pref=FirebaseStorage.getInstance().getReference("/profile_pics/$f_pname")
        f_pref.putFile(selected_pic!!)
            .addOnSuccessListener {
                Log.d("pic upload","Succesfull ${it.metadata?.path}")

                f_pref.downloadUrl.addOnSuccessListener {
                    Log.d("pic dwnld","$it")

                    saveUsertoFDataBase(it.toString())
                }
            }
            .addOnFailureListener(){
                Log.d("user upload","failed")
            }
    }

    fun saveUsertoFDataBase(profilePicUrl: String){
        val uid = FirebaseAuth.getInstance().uid

        val u_ref=FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid!!,usr_name_field.text.toString(),profilePicUrl)
        u_ref.setValue(user)
            .addOnSuccessListener {
                Log.d("final","saved user to fdatabase")

            }
            .addOnFailureListener {
                Log.d("final","failed to save user to fdatabase:${it.message}")
            }
    }
}

class User(val uid:String,val userName:String,val profilePicUrl:String)
