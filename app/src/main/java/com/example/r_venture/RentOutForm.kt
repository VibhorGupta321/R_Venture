package com.example.r_venture
//Rventure
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RentOutForm : AppCompatActivity() {
    private lateinit var upload_image:ImageView
    private lateinit var browse_button: Button
    private lateinit var upload_button: Button
    private lateinit var database : DatabaseReference
    private lateinit var company : EditText
    private lateinit var modelnumber : EditText
    private lateinit var pickupaddress : EditText
    private lateinit var price : EditText
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_out_form)
        upload_image=findViewById(R.id.uploadimage)
        browse_button=findViewById(R.id.browsebutton)
        upload_button=findViewById(R.id.uploadbutton)
        val galleryImage=registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if(it!=null){
                    upload_image.setImageURI(it)
                    browse_button.text="Change Image"
                    browse_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.edit_icon, 0, 0, 0);
                }
            })
        browse_button.setOnClickListener{
            galleryImage.launch("image/*")
        }
        company=findViewById(R.id.textInputCompany)
        modelnumber=findViewById(R.id.textInputModel)
        pickupaddress=findViewById(R.id.textInputAddress)
        price=findViewById(R.id.textInputRate)
        database= FirebaseDatabase.getInstance().getReference("cycle")

        upload_button.setOnClickListener {
            savecycledata()
        }


    }

    private fun savecycledata() {
        val c_name = company.text.toString()
        val m_num = modelnumber.text.toString()
        val p_add = pickupaddress.text.toString()
        val Price = price.text.toString()
        if(c_name.isEmpty())
        {
            company.error = "please enter company name"
        }
        if(m_num.isEmpty())
        {
            modelnumber.error = "please enter model number"
        }
        if(p_add.isEmpty())
        {
            pickupaddress.error = "please enter pickupaddress"
        }
        if(Price.isEmpty())
        {
            price.error = "Enter Price"
        }

        val c_id = database.push().key!!


        val Cycle = cycle_data(c_id,c_name,m_num,p_add,Price)



        database.child(c_id).setValue(Cycle).addOnCompleteListener{

            val intent1 = Intent(this@RentOutForm, Dashboard::class.java)
            startActivity(intent1)
            Toast.makeText(this,"Successfully Uploded Data",Toast.LENGTH_LONG).show()


        }.addOnFailureListener { err->
            Toast.makeText(this,"Failed ${err.message}",Toast.LENGTH_LONG).show()
        }

    }

}
