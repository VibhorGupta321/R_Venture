package com.example.r_venture
//Rventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    lateinit var pnoinput: EditText
    lateinit var nextbutton: Button
    lateinit var uname: EditText
    lateinit var uemail: EditText
    private var mcb: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private lateinit var fb: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fb = FirebaseAuth.getInstance()
//        gsignin=findViewById(R.id.button2)
        pnoinput = findViewById(R.id.textInputEditText)
        nextbutton = findViewById(R.id.next_button)
        uname = findViewById(R.id.PersonName)
        uemail = findViewById(R.id.EmailAddress)



        nextbutton.setOnClickListener {

            val u_name = uname.text.toString()
            val phone_num = pnoinput.text.toString()
            val u_email = uemail.text.toString()

            database = FirebaseDatabase.getInstance().getReference("user")
            val u_id = database.push().key!!
            val appuser = user(u_id,u_name,phone_num,u_email)

            database.child(u_id).setValue(appuser).addOnCompleteListener{
                Toast.makeText(this,"Successfully Uploded Data",Toast.LENGTH_SHORT).show()
                sendotp()
            }.addOnFailureListener { err->
                Toast.makeText(this,"Failed ${err.message}",Toast.LENGTH_LONG).show()
            }

        }
//        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(R.string.default_web_client_id.toString())
//            .requestEmail().build()
//        client=GoogleSignIn.getClient(this@Login, options)
//        gsignin.setOnClickListener{
//            val intent=client.signInIntent
//            startActivityForResult(intent, 10001)
//        }
    }

    private fun sendotp() {
        val progbar: ProgressBar = findViewById(R.id.loadingOTPScreen)
        if (pnoinput.text.toString().trim().isNotEmpty()) {
            if ((pnoinput.text.toString().trim()).length == 10) {
                progbar.visibility = View.VISIBLE
                nextbutton.visibility = View.GONE

                mcb = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        progbar.visibility = View.GONE
                        nextbutton.visibility = View.VISIBLE
                    }


                    override fun onVerificationFailed(p0: FirebaseException) {
                        p0.printStackTrace()
                        progbar.visibility = View.GONE
                        nextbutton.visibility = View.VISIBLE
                        Toast.makeText(this@Login, p0.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        progbar.visibility = View.GONE
                        nextbutton.visibility = View.VISIBLE
                        Toast.makeText(this@Login, "OTP Sent", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, enterotp::class.java)
                        intent.putExtra("mobno", pnoinput.text.toString())
                        intent.putExtra("actualotp", p0)
                        startActivity(intent)
                    }
                }

                var x=PhoneAuthOptions.newBuilder(fb).setPhoneNumber("+91" + pnoinput.text.toString())
                    .setTimeout(60L, TimeUnit.SECONDS).setActivity(this@Login)
                    .setCallbacks(mcb as PhoneAuthProvider.OnVerificationStateChangedCallbacks).build()
                PhoneAuthProvider.verifyPhoneNumber(x)
            } else {
                Toast.makeText(this@Login, "Enter Valid Number", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@Login, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
        }
    }

//    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==10001){
//            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account=task.getResult(ApiException::class.java)
//            val credential=GoogleAuthProvider.getCredential(account.idToken, null)
//            FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if(task.isSuccessful){
//                        val i = Intent(this@Login, enterotp::class.java)
//                        startActivity(i)
//                    }
//                    else{
//                        Toast.makeText(this@Login, task.exception?.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null)
        {
            val i = Intent(this@Login, Dashboard::class.java)
            startActivity(i)
        }
    }

}
