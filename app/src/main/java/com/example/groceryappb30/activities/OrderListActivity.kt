package com.example.groceryappb30.activities

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.OLAdapter
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityOrderListBinding
import com.example.groceryappb30.databinding.AppBarBinding

class OrderListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderListBinding
    lateinit var olAdapter: OLAdapter
    private lateinit var appBarBinding: AppBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        var view = binding.root


        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))

        init()


    }

    private fun init() {
        olAdapter = OLAdapter(this)

        getData()
        setupToolbar()

        binding.recyclerViewOrderReview.adapter = olAdapter
        binding.recyclerViewOrderReview.layoutManager = LinearLayoutManager(this)

        binding.buttonSettings.setOnClickListener{
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAccount.setOnClickListener {
            var intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        var sessionManager = SessionManager(this)

        sessionManager.getOrders(this, olAdapter)

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
