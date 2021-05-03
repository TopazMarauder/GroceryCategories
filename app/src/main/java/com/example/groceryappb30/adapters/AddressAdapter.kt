package com.example.groceryappb30.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.R
import com.example.groceryappb30.activities.AddressActivity
import com.example.groceryappb30.activities.AddressAddActivity
import com.example.groceryappb30.activities.CartActivity
import com.example.groceryappb30.activities.PaymentActivity
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.app.SessionManager
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityAddressBinding
import com.example.groceryappb30.databinding.AdapterAddressBinding
import com.example.groceryappb30.models.Address
import com.example.groceryappb30.models.AddressResponse
import com.example.groceryappb30.models.Product
import com.example.groceryappb30.models.User
import com.google.gson.Gson

class AddressAdapter(var mContext: Context) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    var mList: ArrayList<Address> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.adapter_address, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var address = mList[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun setData(list: ArrayList<Address>) {
        mList = list
        notifyDataSetChanged()

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding: AdapterAddressBinding

        @SuppressLint("SetTextI18n")
        fun bind(address: Address) {
            binding = AdapterAddressBinding.bind(itemView)
            binding.textViewAddressName.text = "${address.houseNo}  ${address.streetName}"
            binding.textViewAddressLocation.text = "${address.city}, ${address.pincode}"




            binding.buttonDelete.setOnClickListener {


                var sessionManager = SessionManager(mContext)





                sessionManager.deleteAddress(mContext, address._id)

                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show()

                var intent = Intent(mContext, AddressActivity::class.java)
                mContext.startActivity(intent)


                sessionManager.getAddresses(mContext, this@AddressAdapter)




            }

            itemView.setOnClickListener {
                var intent = Intent(mContext, PaymentActivity::class.java)
                intent.putExtra(Address.KEY_ADDRESS, address)
                mContext.startActivity(intent)

            }


        }


    }
}

