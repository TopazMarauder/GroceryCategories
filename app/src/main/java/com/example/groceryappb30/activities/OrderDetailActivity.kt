package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.ProductAdapter
import com.example.groceryappb30.app.Config.Companion.FIlE_NAME
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.databinding.ActivityOrderDetailBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.*
import com.google.gson.Gson

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding

    lateinit var productAdapter: ProductAdapter

    private lateinit var appBarBinding: AppBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))



        init()
    }

    private fun init() {

        productAdapter = ProductAdapter(this)

        getData()



        binding.recyclerViewProduct.adapter = productAdapter
        binding.recyclerViewProduct.layoutManager = LinearLayoutManager(this)


        binding.buttonSettings.setOnClickListener {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAccount.setOnClickListener {
            var intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getData() {

        var sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString(User.KEY_UID, "empty?")
        var orderId = intent.getStringExtra("orderid")

        var requestQueue = Volley.newRequestQueue(this)

        @Suppress("RedundantSamConstructor")
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrder(userId!!),
            Response.Listener {
                var gson = Gson()
                var olResponse = gson.fromJson(it, OrderResponseAlt::class.java)
                var trueProductList: ArrayList<Product> = ArrayList()
                for (order in olResponse.data) {

                    if (order._id == orderId) {

                        for (product in order.products) {
                            var defaultProduct = Product(
                                1,
                                product._id,
                                1,
                                "empty",
                                "empty",
                                product.image,
                                product.mrp,
                                0,
                                product.price,
                                product.productName,
                                product.quantity,
                                true,
                                1,
                                "unit"
                            )
                            trueProductList.add(defaultProduct)
                        }

                        binding.textViewTotal.text = "$ "+ order.orderSummary.totalAmount.toString()
                        binding.houseStreet.text = order.shippingAddress.houseNo + " " + order.shippingAddress.streetName
                        binding.cityPin.text = order.shippingAddress.city + ", " + order.shippingAddress.pincode.toString()
                        binding.textViewDate.text = order.date
                    }
                }
                productAdapter.setData(trueProductList, 1)
            },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)


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