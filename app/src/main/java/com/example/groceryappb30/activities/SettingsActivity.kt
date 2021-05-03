package com.example.groceryappb30.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.AddressAdapter
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityAccountBinding
import com.example.groceryappb30.databinding.ActivitySettingsBinding
import com.example.groceryappb30.databinding.AppBarBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    lateinit var addressAdapter: AddressAdapter
    private lateinit var appBarBinding: AppBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
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
            startActivity(intent)

        }

        binding.buttonAccount.setOnClickListener {
            var intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)

        }

        binding.buttonOrders.setOnClickListener {
            var intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getData() {

        var sessionManager = SessionManager(this)


        sessionManager.getAddresses(this, addressAdapter)

    }


    private fun setupToolbar() {
        appBarBinding.toolbar.title = "Settings"
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

        }
        return true
    }
}