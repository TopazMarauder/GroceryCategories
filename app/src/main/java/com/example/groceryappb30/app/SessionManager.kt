package com.example.groceryappb30.app

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Log.ASSERT
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.activities.AddressActivity
import com.example.groceryappb30.activities.CategoryActivity
import com.example.groceryappb30.adapters.AddressAdapter
import com.example.groceryappb30.adapters.OLAdapter
import com.example.groceryappb30.models.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject

class SessionManager(val mContext: Context) {

    private val FIlE_NAME = "myPrefs"

    private val KEY_NAME = "name key"
    private val KEY_EMAIL = "email key"
    private val KEY_PASSWORD = "password key"
    private val KEY_LOGGED_IN = "logged in key"
    private val KEY_MOBILE = "mobile key"

    var sharedPreferences = mContext.getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()


    fun registerUser(name: String, email: String, password: String, phone: String) {

        var jsonObject = JSONObject()
        jsonObject.put("firstName", name)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("mobile", phone)


        var requestQueue = Volley.newRequestQueue(mContext)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postRegister(),
            jsonObject,
            {
                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d("abc", it.toString())
            },
            {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonRequest)


    }

    fun putUser(id: String,key: String ,name: String){

        var jsonObject = JSONObject()
        jsonObject.put(key, name)

        var requestQueue = Volley.newRequestQueue(mContext)
        var jsonRequest = JsonObjectRequest(
            Request.Method.PUT,
            Endpoints.putUser(id),
            jsonObject,
            {
                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d("abc", it.toString())
            },
            {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonRequest)


    }

    fun registerAddress(
        house: String,
        street: String,
        city: String,
        pincode: Int,
        type: String,
        uID: String
    ) {
        var jsonObject = JSONObject()
        jsonObject.put("pincode", pincode)
        jsonObject.put("city", city)
        jsonObject.put("houseNo", house)
        jsonObject.put("streetName", street)
        jsonObject.put("type", type)
        jsonObject.put("userId", uID)


        var requestQueue = Volley.newRequestQueue(mContext)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postAddress(),
            jsonObject,
            {
                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d("abc", it.toString())
            },
            {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }

        )

        requestQueue.add(jsonRequest)

    }


    fun getAddresses(mContext: Context, addressAdapter: AddressAdapter, addressActivity: AddressActivity? = null) {


        var sharedPreferences = mContext.getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString(User.KEY_UID, "empty?")

        var requestQueue = Volley.newRequestQueue(mContext)

        @Suppress("RedundantSamConstructor")
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getAddress(userId!!),
            Response.Listener {
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                addressAdapter.setData(addressResponse.data)
                if (addressResponse.data.isNotEmpty() && addressActivity != null){
                    addressActivity.dataKita()
                }

            },
            Response.ErrorListener {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
    }


    fun  deleteAddress(mContext: Context, id: String){


        var requestQueue = Volley.newRequestQueue(mContext)

        @Suppress("RedundantSamConstructor")
        var request = StringRequest(
            Request.Method.DELETE,
            Endpoints.deleteAddress(id),
            Response.Listener {

            },
            Response.ErrorListener {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)


    }








    fun checkStatus(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
    }

    fun logout() {
        editor.putBoolean(KEY_LOGGED_IN, false)
        editor.commit()


    }



    fun postOrder(
        orderResponse: OrderResponse
    ){

        var gson = Gson()
        var zzz = gson.toJson(orderResponse)
        var jsonObject = JSONObject(zzz)
       /* jsonObject.put("orderSummary", orderResponse.orderSummary)
        jsonObject.put("payment", orderResponse.payment)
        jsonObject.put("products", orderResponse.products)
        jsonObject.put("shippingAddress", orderResponse.shippingAddress)
        jsonObject.put("user", orderResponse.user)
        jsonObject.put("userId", orderResponse.userId)*/


        Log.d("abc", jsonObject.toString())


        var requestQueue = Volley.newRequestQueue(mContext)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postOrder(),
            jsonObject,
            {
                Log.d("abc", it.toString())

            },
            {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())    }

        )

        requestQueue.add(jsonRequest)




    }
    //http://grocery-second-app.herokuapp.com/api/orders/5e53e8cde4d77e0017d85a96
    fun getOrders(mContext: Context, olAdapter: OLAdapter){
        var sharedPreferences = mContext.getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString(User.KEY_UID, "empty?")

        var requestQueue = Volley.newRequestQueue(mContext)

        @Suppress("RedundantSamConstructor")
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrder(userId!!),
            Response.Listener {
                var gson = Gson()
                var olResponse = gson.fromJson(it, OrderResponseAlt::class.java)
                olAdapter.setData(olResponse.data)


            },
            Response.ErrorListener {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)

    }
}






/*    fun login(email: String, password: String): Boolean{

        var jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)



        var requestFinished = false
        var requestSuccess :Boolean = true

        var requestQueue = Volley.newRequestQueue(mContext)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getLogin(),
            jsonObject,
            Response.Listener{
                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.v("mymess", it.toString())


                var gson = Gson()
                var userResponse = gson.fromJson(it.toString(), UserResponse::class.java)
                var userData = userResponse.user






                editor.putString(KEY_NAME, userData.firstName)
                editor.putString(KEY_MOBILE, userData.mobile)


                requestFinished = true


            },
            {
                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()

                requestSuccess = false
            }
        )




        requestQueue.add(jsonRequest)


        if (!requestSuccess){
            return false
        }


        editor.putString(KEY_EMAIL, email)
        editor.putBoolean(KEY_LOGGED_IN, true)
        editor.commit()

        while (!requestFinished){
            Toast.makeText(mContext, "please wait", Toast.LENGTH_SHORT).show()
        }





        return true

    }*/