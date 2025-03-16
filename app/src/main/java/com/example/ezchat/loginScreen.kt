package com.example.ezchat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class loginScreen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userinfodb: FirebaseFirestore
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        var login_btn = findViewById<Button>(R.id.login)
        var signup_text = findViewById<TextView>(R.id.signup_text)
        val pgbar = findViewById<ProgressBar>(R.id.pgbar)
        firebaseAuth = FirebaseAuth.getInstance()
        userinfodb = FirebaseFirestore.getInstance()
        login_btn.setOnClickListener {
            login_btn.visibility = View.GONE
            pgbar.visibility = View.VISIBLE
            var user_email = findViewById<EditText>(R.id.user_login_email)
            var user_pass = findViewById<EditText>(R.id.user_login_pass)

            if (verifyfields(user_email, user_pass)) {
                firebaseAuth.signInWithEmailAndPassword(user_email.text.toString(), user_pass.text.toString()).addOnSuccessListener{
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        if(user.isEmailVerified){
                            Toast.makeText(this,"Log in Successfull ",
                                Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Please Verify Your Email ",
                                Toast.LENGTH_LONG).show()
                        }
                    }



                }
                    .addOnFailureListener{exception->
                        val errormessage = exception.message
                        Toast.makeText(this,"Failed due to $errormessage",
                            Toast.LENGTH_LONG).show()
                    }
            }
            else{
                login_btn.visibility = View.VISIBLE
                pgbar.visibility = View.GONE
            }


        }
        signup_text.setOnClickListener{
            startActivity(Intent(this,signUpscreen::class.java))
            finish()
        }

    }

    fun verifyfields(userEmail: EditText, userPass: EditText):Boolean{
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()){
            userEmail.setError("Enter Valid Email Address")
            return false
        }
        if(userPass.text.toString().length<8){
            userPass.setError("Password must be < 8")
            return false
        }
        return true
    }
}
