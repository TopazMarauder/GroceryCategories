package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.AddressAdapter
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityAddressBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.AddressResponse
import com.example.groceryappb30.models.User
import com.google.gson.Gson

class AddressActivity : AppCompatActivity() {

    private val FIlE_NAME = "myPrefs"

    private lateinit var binding: ActivityAddressBinding
    lateinit var addressAdapter: AddressAdapter
    private lateinit var appBarBinding: AppBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))



        init()
    }


    private fun init() {
        addressAdapter = AddressAdapter(this)

        getData()
        setupToolbar()

        binding.recyclerViewAddress.adapter = addressAdapter
        binding.recyclerViewAddress.layoutManager = LinearLayoutManager(this)


        binding.buttonAdd.setOnClickListener {
            var intent = Intent(this, AddressAddActivity::class.java)
            intent.putExtra("where", "cart")
            startActivity(intent)

        }

        


    }

    private fun getData() {

        var sessionManager = SessionManager(this)


        sessionManager.getAddresses(this, addressAdapter, this)

    }


    fun dataKita(){
        binding.textViewNoItem.visibility = View.GONE
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