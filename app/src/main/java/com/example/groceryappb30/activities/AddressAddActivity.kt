package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.groceryappb30.R
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityAddressAddBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.User

class AddressAddActivity : AppCompatActivity() {

    private val FIlE_NAME = "myPrefs"

    private lateinit var appBarBinding: AppBarBinding

    private lateinit var binding: ActivityAddressAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressAddBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))

        init()
    }

    private fun init() {

        setupToolbar()


        binding.button.setOnClickListener {
            var house = binding.textHouse.editableText.toString()
            var street = binding.textStreet.editableText.toString()
            var city = binding.textCity.editableText.toString()
            var pincode = binding.textPincode.editableText.toString().toInt()

            var sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
            var userId = sharedPreferences.getString(User.KEY_UID, "empty?")


            var sessionManager = SessionManager(this)
            sessionManager.registerAddress(house, street, city, pincode, "House", userId!!)


            if(intent.getStringExtra("where") == "cart"){
                var intent = Intent(this, AddressActivity::class.java)
                startActivity(intent)
            }
            else{
                var intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)

            }




        }
    }

    private fun setupToolbar() {
        appBarBinding.toolbar.title = "Addresses"
        setSupportActionBar(appBarBinding.toolbar)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_account -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)

            }
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_home -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_orders -> {
                val intent = Intent(this, OrderListActivity::class.java)
                startActivity(intent)
            }

        }
        return true
    }
}