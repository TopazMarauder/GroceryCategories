package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryappb30.app.Config.Companion.FIlE_NAME
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityPaymentBinding
import com.example.groceryappb30.models.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    private lateinit var dbHelper: DBHelper

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        init()
    }

    private fun init() {

        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)





        updatePrice(dbHelper.getPrices())

        val orderResponse = getData()




        binding.textViewPayLater.setOnClickListener {
            sessionManager.postOrder(orderResponse)

            var intentOut = Intent(this, MapsActivity::class.java)
            startActivity(intentOut)

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

        var deliverycharge = 10
        val total = deliverycharge + priceSet.third
        val ourPrice = priceSet.third
        val orderAmount = priceSet.first.first
        val discount = priceSet.first.second


//Triple(Pair(mrpSum, discount), deliveryFee, total)
        if (priceSet.second == "free") {
            deliverycharge = 0
        }


        binding.textViewTotaling.text =
            " ${priceSet.third} \n ${priceSet.second} \n 0 \n ${priceSet.third + deliverycharge} "

        binding.textViewFinalPrice.text = "Your Total: $ ${priceSet.third + deliverycharge}"

        return OrderSummary(deliverycharge, discount, orderAmount, ourPrice, total)
    }
}