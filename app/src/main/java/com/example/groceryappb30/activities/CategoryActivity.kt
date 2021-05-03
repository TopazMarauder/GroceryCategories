package com.example.groceryappb30.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.R
import com.example.groceryappb30.adapters.CategoryAdapter
import com.example.groceryappb30.app.Config.Companion.FIlE_NAME
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.databinding.ActivityCategoryBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.databinding.ContentMainBinding
import com.example.groceryappb30.databinding.NavHeaderBinding
import com.example.groceryappb30.models.Category
import com.example.groceryappb30.models.CategoryResponse
import com.example.groceryappb30.models.User.Companion.KEY_EMAIL
import com.example.groceryappb30.models.User.Companion.KEY_NAME
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {




    lateinit var drawLayout: DrawerLayout
    lateinit var navView: NavigationView


    var mList: ArrayList<Category> = ArrayList()
    lateinit var categoryAdapter: CategoryAdapter

    private lateinit var binding: ContentMainBinding

    private lateinit var headerBinding: NavHeaderBinding

    private lateinit var categoryBinding: ActivityCategoryBinding

    private lateinit var appBarBinding: AppBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ContentMainBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)


        categoryBinding = ActivityCategoryBinding.bind(view.findViewById(R.id.activity_category))
        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))

        init()
    }

    private fun init() {
        categoryAdapter = CategoryAdapter(this)
        getData()
        setupToolbar()



        drawLayout = binding.drawerLayout
        navView = binding.navView




        categoryBinding.recyclerViewMain.adapter = categoryAdapter
        categoryBinding.recyclerViewMain.layoutManager = GridLayoutManager(this, 2)
        categoryBinding.recyclerViewMain.addItemDecoration(DividerItemDecoration(this, GridLayoutManager.HORIZONTAL))
        categoryBinding.recyclerViewMain.addItemDecoration(DividerItemDecoration(this, GridLayoutManager.VERTICAL))


        categoryBinding.imageViewBanner.setImageResource(R.drawable.grocery_sale_temp)






        var sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)



        headerBinding = NavHeaderBinding.bind(navView.getHeaderView(0))
        headerBinding.userName.text = sharedPreferences.getString(KEY_NAME,"Guest")
        headerBinding.userEmail.text = sharedPreferences.getString(KEY_EMAIL,"Guest")


        var toggle = ActionBarDrawerToggle(
            this, drawLayout, appBarBinding.toolbar, 0,0
        )
        drawLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


    }//.load.placeholder.error.into



    private fun getData() {
        val requestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            {
                val gson = Gson()
                val categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                categoryAdapter.setData(categoryResponse.data)
                categoryBinding.progressBar.visibility = View.GONE

            },
            {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
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



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_account -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
            }
            R.id.item_log -> logoutDialogue()
        }
        drawLayout.closeDrawer(GravityCompat.START)
        return true
    }




    private fun logoutDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you wan to logout")
        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(applicationContext, "Logging out...", Toast.LENGTH_SHORT).show()
            }
        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })
        builder.create().show()
    }




    override fun onBackPressed() {
        if(drawLayout.isDrawerOpen(GravityCompat.START)){
            drawLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}