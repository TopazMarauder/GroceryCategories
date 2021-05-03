package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.groceryappb30.R
import com.example.groceryappb30.app.Config
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityAccountBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.User

class AccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAccountBinding

    private lateinit var sessionManager: SessionManager

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appBarBinding: AppBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))



        init()
    }

    private fun init() {

        var sharedPreferences = getSharedPreferences(Config.FIlE_NAME, Context.MODE_PRIVATE)

        setupToolbar()


        binding.textViewName.text = sharedPreferences.getString(User.KEY_NAME, "badName")

        binding.textViewEmail.text = sharedPreferences.getString(User.KEY_EMAIL, "badName")

        sessionManager = SessionManager(this)

        binding.buttonSettings.setOnClickListener(this)
        binding.buttonOrders.setOnClickListener(this)
        binding.imageEditName.setOnClickListener(this)
        binding.imageEditEmail.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v){

            binding.buttonSettings -> {
                var intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

            binding.buttonOrders -> {
                var intent = Intent(this, OrderListActivity::class.java)
                startActivity(intent)
            }
            binding.imageEditName -> {


                if(binding.textViewName.visibility == View.VISIBLE){
                    binding.textViewName.visibility = View.INVISIBLE
                    binding.editTextName.visibility = View.VISIBLE
                }else{


                    var userId = sharedPreferences.getString(User.KEY_UID, "badUID")
                    var newName = binding.editTextName.editableText.toString()


                    sharedPreferences.edit().putString(User.KEY_NAME, newName)

                    sessionManager.putUser(userId!!, "firstName",newName)



                    binding.textViewName.text = binding.editTextName.editableText.toString()
                    binding.textViewName.visibility = View.VISIBLE
                    binding.editTextName.visibility = View.INVISIBLE


                }




            }
            binding.imageEditEmail -> {

            }

        }


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