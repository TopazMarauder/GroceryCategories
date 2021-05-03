package com.example.groceryappb30.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.databinding.ActivityLoginBinding
import com.example.groceryappb30.models.User
import com.example.groceryappb30.models.UserResponse
import com.google.gson.Gson
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private val FIlE_NAME = "myPrefs"



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root

        super.onCreate(savedInstanceState)
        setContentView(view)


        init()
    }

    private fun init() {
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v){
            binding.buttonLogin ->{
                var givenEmail = binding.enterEmail.editableText.toString()
                var givenPassword = binding.enterPassword.editableText.toString()
                login(givenEmail, givenPassword)



            }
            binding.buttonRegister ->{

                var intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }



    private fun login(email: String, password: String) {
        var sharedPreferences = getSharedPreferences(FIlE_NAME, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()



        var jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)




        var requestQueue = Volley.newRequestQueue(this)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getLogin(),
            jsonObject,
            Response.Listener{
                //Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                Log.v("mymess", it.toString())


                var gson = Gson()
                var userResponse = gson.fromJson(it.toString(), UserResponse::class.java)
                var userData = userResponse.user





                editor.putString(User.KEY_NAME, userData.firstName)
                editor.putString(User.KEY_MOBILE, userData.mobile)
                editor.putString(User.KEY_EMAIL, email)
                editor.putString(User.KEY_UID, userData._id)

                editor.putBoolean(User.KEY_LOGGED_IN, true)
                editor.commit()


                var intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)

            },
            {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()


            }
        )




        requestQueue.add(jsonRequest)





    }
}