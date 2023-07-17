package com.example.r_venture
//Rventure
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController=navHostFragment.navController
        val bottomNavView=findViewById<BottomNavigationView>(R.id.bottomNavBar)
        setupWithNavController(bottomNavView, navController)
        val rentOut_Card: RelativeLayout = findViewById(R.id.rentOutCard)
        val rentIn_Card: RelativeLayout = findViewById(R.id.rentInCard)
        rentIn_Card.setOnClickListener{
            val intent1 = Intent(this@Dashboard, RentOutForm::class.java)
            startActivity(intent1)
        }
        rentOut_Card.setOnClickListener{
            val intent1 = Intent(this@Dashboard, Rent::class.java)
            startActivity(intent1)
        }
    }
}
