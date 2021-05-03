package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.groceryappb30.R
import com.example.groceryappb30.app.Config.Companion.FIlE_NAME
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityPaymentBinding
import com.example.groceryappb30.databinding.AppBarBinding
import com.example.groceryappb30.models.*
import com.google.android.material.snackbar.Snackbar

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    private lateinit var dbHelper: DBHelper

    private lateinit var sessionManager: SessionManager

    private lateinit var appBarBinding: AppBarBinding

    var deliverycharge = 10
    var total = 0
    var ourPrice = 0
    var orderAmount = 0
    var discount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        appBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))

        init()
    }

    private fun init() {

        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)


        setupToolbar()

        updatePrice(dbHelper.getPrices())

        var orderResponse = getData()




        binding.textViewPayLater.setOnClickListener {
            sessionManager.postOrder(orderResponse)

            var intentOut = Intent(this, MapsActivity::class.java)
            startActivity(intentOut)

        }
        binding.inputLayoutPassword.setEndIconOnClickListener {
           if (binding.couponEdit.editableText.toString() == "this"){
               orderResponse.orderSummary.discount += 20
               orderResponse.orderSummary.totalAmount -= 20

               ourPrice -= 20
               total -= 20

               binding.textViewTotaling.text =
                   " ${ourPrice} \n ${deliverycharge} \n 0 \n ${total} "

               binding.textViewFinalPrice.text = "Your Total: $ ${total}"

               Snackbar.make(binding.root, "You got a discount", Snackbar.LENGTH_SHORT).show()
           }
        }
    }


    private fun getData(): OrderResponse {

        val sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)

        val products = dbHelper.getOrderProducts()
        val orderSummary = updatePrice(dbHelper.getPrices())
        val payment = Payment("cash", "completed")
        val chosenAddress = intent.getSerializableExtra(Address.KEY_ADDRESS) as Address
        val shippingAddress = ShippingAddress(
            chosenAddress.city,
            chosenAddress.houseNo,
            chosenAddress.pincode,
            chosenAddress.streetName,
            chosenAddress.type
        )
        val orderUser = OrderUser(
            sharedPreferences.getString(User.KEY_EMAIL, "Guest")!!,
            sharedPreferences.getString(User.KEY_MOBILE, "12345")!!
        )


        return OrderResponse(
            orderSummary,
            payment,
            products,
            shippingAddress,
            orderUser,
            sharedPreferences.getString(User.KEY_UID,"BAD UID")!!

            )
    }


    private fun updatePrice(priceSet: Triple<Pair<Int, Int>, String, Int>): OrderSummary {

        deliverycharge = 10

        ourPrice = priceSet.third
        orderAmount = priceSet.first.first
        discount = priceSet.first.second


//Triple(Pair(mrpSum, discount), deliveryFee, total)
        if (priceSet.second == "free") {
            deliverycharge = 0
        }
        total = deliverycharge + priceSet.third

        binding.textViewTotaling.text =
            " ${ourPrice} \n ${deliverycharge} \n 0 \n ${total} "

        binding.textViewFinalPrice.text = "Your Total: $ ${total}"


        return OrderSummary(deliverycharge, discount, orderAmount, ourPrice, total)
    }


    private fun setupToolbar() {
        appBarBinding.toolbar.title = "Check Out"
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