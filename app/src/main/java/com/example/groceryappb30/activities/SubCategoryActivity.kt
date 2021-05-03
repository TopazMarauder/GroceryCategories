package com.example.groceryappb30.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.SectionsPagerAdapter
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.databinding.ActivitySubCategoryBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.*
import com.google.gson.Gson

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubCategoryBinding
    var subcategoryList: ArrayList<Subcategory> = ArrayList()
    private lateinit var appBarBinding: AppBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))

        init()


    }

    private fun init() {


        var category= intent.getSerializableExtra(Category.KEY_CATEGORY) as Category

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = binding.viewPagerSub

        val tabs: TabLayout = binding.tabLayoutSub


        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategory(category.catId),
            {
                var gson = Gson()
                var subcategoryResponse = gson.fromJson(it, SubcategoryResponse::class.java)

                for( item in subcategoryResponse.data){
                    subcategoryList.add(item)

                    sectionsPagerAdapter.addFragment(item)


                }


                viewPager.adapter = sectionsPagerAdapter

                tabs.setupWithViewPager(viewPager)

            },
            {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
        //getSubCategories()


        setupToolbar(category)

    }


    private fun setupToolbar(category: Category) {
        appBarBinding.toolbar.title = category.catName
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