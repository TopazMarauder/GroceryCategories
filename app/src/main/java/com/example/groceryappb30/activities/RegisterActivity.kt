package com.example.groceryappb30.activities

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.groceryappb30.R
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        when(v?.id) {
            R.id.button_register ->{
                var givenName = binding.enterUsername.editableText.toString()
                var givenEmail = binding.enterEmail.editableText.toString()
                var givenMobile = binding.enterMobile.editableText.toString()

                if (binding.enterPasswordprimary.editableText.toString() == binding.enterPasswordsecondary.editableText.toString()){
                    var givenPassword = binding.enterPasswordprimary.editableText.toString()

                    var sessionManager = SessionManager(this)
                    sessionManager.registerUser(givenName,givenEmail,givenPassword, givenMobile)





                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)


                }
                else{
                    Toast.makeText(applicationContext, "passwords do not match", Toast.LENGTH_LONG).show()


                }

            }

            R.id.button_login -> {

                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }



        }
    }
}