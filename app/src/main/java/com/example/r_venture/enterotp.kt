package com.example.r_venture
//Rventure
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

class enterotp : AppCompatActivity() {
    lateinit var num1: EditText
    lateinit var num2: EditText
    lateinit var num3: EditText
    lateinit var num4: EditText
    lateinit var num5: EditText
    lateinit var num6: EditText
    lateinit var showmobno: TextView
    lateinit var actualotp: String
    lateinit var verify: Button
    lateinit var probar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterotp)
        probar=findViewById(R.id.loadingVerifyScreen)
        num1 = findViewById(R.id.otp_num1)
        num2 = findViewById(R.id.otp_num2)
        num3 = findViewById(R.id.otp_num3)
        num4 = findViewById(R.id.otp_num4)
        num5 = findViewById(R.id.otp_num5)
        num6 = findViewById(R.id.otp_num6)
        verify = findViewById(R.id.verify_button)
        showmobno = findViewById(R.id.mobile_number)
        actualotp = intent.getStringExtra("actualotp").toString()
        showmobno.text = String.format("+91-%s", intent.getStringExtra("mobno"))
        verify.setOnClickListener {
            matchotp()
        }
        cursormove()
        findViewById<TextView>(R.id.resend_otp).setOnClickListener {
            findViewById<TextView>(R.id.resend_otp).setTextColor(Color.parseColor("#4E1920"))
            val fb = FirebaseAuth.getInstance()
            val mcb = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    findViewById<TextView>(R.id.resend_otp).setTextColor(Color.parseColor("#FF000000"))
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    p0.printStackTrace()
                    findViewById<TextView>(R.id.resend_otp).setTextColor(Color.parseColor("#FF000000"))
                    Toast.makeText(this@enterotp, p0.message, Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    actualotp = p0
                    findViewById<TextView>(R.id.resend_otp).setTextColor(Color.parseColor("#FF000000"))
                    Toast.makeText(this@enterotp, "New OTP Sent", Toast.LENGTH_SHORT).show()
                }
            }
            var y= PhoneAuthOptions.newBuilder(fb).setPhoneNumber("+91" + intent.getStringExtra("mobno"))
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this@enterotp)
                .setCallbacks(mcb as PhoneAuthProvider.OnVerificationStateChangedCallbacks).build()
            PhoneAuthProvider.verifyPhoneNumber(y)
        }
    }

    private fun matchotp() {
        if (num1.text.toString().trim().isNotEmpty() && num2.text.toString().trim()
                .isNotEmpty() && num3.text.toString().trim().isNotEmpty() && num4.text.toString().trim().isNotEmpty()&&
            num5.text.toString().trim().isNotEmpty()&& num6.text.toString().trim().isNotEmpty()
        ) {
            val userotp: String =
                num1.text.toString() + num2.text.toString() + num3.text.toString() + num4.text.toString() + num5.text.toString() + num6.text.toString()
            if (actualotp != null) {
                verify.visibility = View.GONE
                probar.visibility = View.VISIBLE
                val pac: PhoneAuthCredential = PhoneAuthProvider.getCredential(actualotp, userotp)
                val res =
                    OnCompleteListener<AuthResult> { p0 ->
                        probar.visibility = View.GONE
                        verify.visibility = View.VISIBLE
                        if (p0.isSuccessful) {
                            val intent = Intent(this@enterotp, Dashboard::class.java)
                            Toast.makeText(
                                this@enterotp,
                                "OTP Verified Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@enterotp,
                                "Incorrect OTP, Try Again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                FirebaseAuth.getInstance().signInWithCredential(pac).addOnCompleteListener(res)
            } else {
                Toast.makeText(this@enterotp, "Check Your Connection", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@enterotp, "Please Enter all Digits", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cursormove() {

        num1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    num2.requestFocus()
                }
            }
        })

        num2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    num3.requestFocus()
                } else {
                    num1.requestFocus()
                }
            }
        })

        num3.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    num4.requestFocus()
                } else {
                    num2.requestFocus()
                }
            }
        })
        num4.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    num5.requestFocus()
                } else {
                    num3.requestFocus()
                }
            }
        })

        num5.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    num6.requestFocus()
                } else {
                    num4.requestFocus()
                }
            }
        })


        num6.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isEmpty()) {
                    num5.requestFocus()
                }
            }
        })
    }
}
