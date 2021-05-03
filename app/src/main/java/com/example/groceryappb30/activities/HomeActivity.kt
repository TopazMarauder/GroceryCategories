package com.example.groceryappb30.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.groceryappb30.R
import com.example.groceryappb30.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private val FIlE_NAME = "myPrefs"
    private val KEY_LOGGED_IN = "logged in key"


    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        var view = binding.root

        super.onCreate(savedInstanceState)
        setContentView(view)

        init()
    }

    private fun init() {
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonRegister.setOnClickListener(this)




        var sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(KEY_LOGGED_IN, false)){
            var intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

    }


    override fun onClick(v: View?){

        when (v?.id){
            R.id.button_login ->{
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.button_register ->{
                var intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

    }
}