package com.example.groceryappb30.activities

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.example.groceryappb30.R
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityOrderConfirmedBinding

class OrderConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderConfirmedBinding

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)


        setContentView(view)


        init()




    }

    private fun init() {

        dbHelper = DBHelper(this)

        dbHelper.deleteAll()
    }


    override fun onBackPressed() {
       var intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)


    }
}