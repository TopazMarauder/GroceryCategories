package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import com.example.groceryappb30.R
import com.example.groceryappb30.app.Config
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityProductBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.Product
import com.squareup.picasso.Picasso

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    lateinit var dbHelper: DBHelper


    lateinit var appBarBinding : AppBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))


        init()
    }

    private fun init() {

        setupToolbar()

        var product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product

        binding.textViewPrice.text = "Price: $ ${product.price}"
        binding.textViewProductName.text = product.productName
        binding.textViewProductUnit.text = "Unit count: ${product.unit} \n ${product.description}"

        binding.textViewProductUnit.movementMethod =  ScrollingMovementMethod()

        Picasso.get()
            .load("${Config.IMAGE_URL + product.image}")
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageViewProduct)


        binding.buttonAddCart.setOnClickListener{
            dbHelper = DBHelper(this)
            dbHelper.addProduct(product)


            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)

        }



    }


    private fun setupToolbar(){
        appBarBinding.toolbar.title = "Category"
        setSupportActionBar(appBarBinding.toolbar)

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_account ->{
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)

            }
            R.id.menu_settings ->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_home ->{
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