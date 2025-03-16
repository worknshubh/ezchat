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

class signUpscreen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userinfodb: FirebaseFirestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_upscreen)
        var signup_btn = findViewById<Button>(R.id.button)
        var login_text = findViewById<TextView>(R.id.login_text)
        firebaseAuth = FirebaseAuth.getInstance()
        userinfodb = FirebaseFirestore.getInstance()
        val pgbar = findViewById<ProgressBar>(R.id.pgbarsignup)
      signup_btn.setOnClickListener {
          signup_btn.visibility = View.GONE
          pgbar.visibility = View.VISIBLE
          var user_name = findViewById<EditText>(R.id.editTextText)
          var user_email = findViewById<EditText>(R.id.editTextTextEmailAddress)
          var user_pass = findViewById<EditText>(R.id.editTextTextPassword)
          var confirm_pass = findViewById<EditText>(R.id.editTextTextPassword2)

          if (verifyfields(user_name, user_email, user_pass, confirm_pass)) {
              firebaseAuth.createUserWithEmailAndPassword(user_email.text.toString(), user_pass.text.toString()).addOnSuccessListener{
                  Toast.makeText(this,"Account Created Successfully Kindly Check Your Email ",Toast.LENGTH_LONG).show()
                  val new_user = userinfo(user_name.text.toString(),user_email.text.toString(),user_pass.text.toString())
                  firebaseAuth.currentUser?.let { it1 ->
                      userinfodb.collection("userinfo").document(
                          it1.uid).set(new_user)
                  }
                  val user = firebaseAuth.currentUser
                  if (user != null) {
                      user.sendEmailVerification()
                  }
                  signup_btn.visibility = View.VISIBLE
                  pgbar.visibility = View.GONE

              }
                  .addOnFailureListener{exception->
                      val errormessage = exception.message
                      Toast.makeText(this,"Failed due to $errormessage",
                          Toast.LENGTH_LONG).show()
                  }
          }
          else{
              signup_btn.visibility = View.VISIBLE
              pgbar.visibility = View.GONE
          }


      }

        login_text.setOnClickListener{
            startActivity(Intent(this,loginScreen::class.java))
            finish()
        }

    }

     fun verifyfields(userName: EditText, userEmail: EditText, userPass: EditText, confirmPass: EditText):Boolean{
        if(userName.length()<6){
            userName.setError("Name must be Greater than 6 characters ")
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()){
            userEmail.setError("Enter Valid Email Address")
            return false
        }
        if(userPass.text.toString().length<8){
            userPass.setError("Password must be < 8")
            return false
        }
        if(userPass.text.toString()!=confirmPass.text.toString()){
            confirmPass.setError("Password not matching")
            return false
        }
        return true
    }
}