package com.example.r_venture
//Rventure
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Rent : AppCompatActivity() {
    private lateinit var cycleRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cycleList: ArrayList<cycle_data>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)

        cycleRecyclerView = findViewById(R.id.rvcycle)
        cycleRecyclerView.layoutManager = LinearLayoutManager(this)
        cycleRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        cycleList = arrayListOf<cycle_data>()

        getData()

    }

    private fun getData() {

        cycleRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("cycle")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cycleList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val cycleData = empSnap.getValue(cycle_data::class.java)
                        cycleList.add(cycleData!!)
                    }
                    val mAdapter = cycleAdapter(cycleList)
                    cycleRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : cycleAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Rent, cart::class.java)

                            //put extras
                            intent.putExtra("Company Name", cycleList[position].company)
                            intent.putExtra("Model Number", cycleList[position].modelnumber)
                            intent.putExtra("Pickup Address", cycleList[position].pickupaddress)
                            intent.putExtra("Price", cycleList[position].price)
                            startActivity(intent)
                        }

                    })

                    cycleRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

