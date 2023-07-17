package com.example.r_venture
//Rventure
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.Hold
import com.google.firebase.database.DatabaseReference

class cart : AppCompatActivity() {

    private lateinit var Rentbtn: Button
    private lateinit var cycleList: ArrayList<cycle_data>
    private lateinit var productname : com.google.android.material.textfield.TextInputEditText
    private lateinit var productprice : com.google.android.material.textfield.TextInputEditText
    private lateinit var productlocation : com.google.android.material.textfield.TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cycleList = arrayListOf<cycle_data>()
        Rentbtn = findViewById(R.id.rentbtn)
        productname = findViewById(R.id.product_name)
        productlocation = findViewById(R.id.product_location)
        productprice = findViewById(R.id.product_price)

        val name : String = intent.getStringExtra("Company Name").toString()
        val price : String = intent.getStringExtra("Price").toString()
        val location : String = intent.getStringExtra("Pickup Address").toString()


        productname.setText(name)
        productprice.setText(price)
        productlocation.setText(location)

        Rentbtn.setOnClickListener {
            val intent = Intent(this@cart, Dashboard::class.java)
            Toast.makeText(this,"Successfully rented",Toast.LENGTH_LONG).show()
            startActivity(intent)

        }


    }


}
