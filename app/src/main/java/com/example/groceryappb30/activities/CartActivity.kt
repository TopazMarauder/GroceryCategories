package com.example.groceryappb30.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.CartAdapter
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityCartBinding
import com.example.groceryappb30.databinding.AppBarBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    lateinit var cartAdapter: CartAdapter
    lateinit var dbHelper: DBHelper
    private lateinit var appBarBinding: AppBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)
        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))


        init()
    }

    private fun init() {
        cartAdapter = CartAdapter(this)
        getData()

        if(dbHelper.checkSize()){
            binding.recyclerViewCart.adapter = cartAdapter
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
            binding.textViewNoItem.visibility = View.GONE
        }

        updatePrice(dbHelper.getPrices())

        setupToolbar()


        binding.buttonReturn.setOnClickListener {
            var intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }
        binding.buttonCheckOut.setOnClickListener {
            var intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)

        }


    }

    private fun getData() {
        dbHelper = DBHelper(this)
        var products = dbHelper.getAllProducts()

        cartAdapter.setData(products)


    }

    fun updatePrice(priceSet: Triple<Pair<Int, Int>, String, Int>){
        binding.textViewPriceValues.text = " ${priceSet.first.first} \n ${priceSet.first.second} \n ${priceSet.second} \n ${priceSet.third} "
    }


    private fun setupToolbar() {
        appBarBinding.toolbar.title = "Cart"
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