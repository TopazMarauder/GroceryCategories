package com.example.groceryappb30.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappb30.R
import com.example.groceryappb30.activities.OrderDetailActivity
import com.example.groceryappb30.activities.ProductActivity
import com.example.groceryappb30.databinding.AdapterOrderListBinding
import com.example.groceryappb30.models.*

class OLAdapter(var mContext: Context) : RecyclerView.Adapter<OLAdapter.ViewHolder>() {

    var mList: ArrayList<Orders> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_list, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = mList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun setData(list: ArrayList<Orders>){
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding: AdapterOrderListBinding


        fun bind(orders: Orders){
            binding = AdapterOrderListBinding.bind(itemView)

            var prolist = ""


            for(item in orders.products){
                prolist += "${item.quantity.toString() + " x " + item.productName} \n"

            }

            binding.textViewDate.text = orders.date
            binding.textViewOrderContents.text  = prolist
            binding.textViewOrderPrice.text = "$ " + orders.orderSummary.totalAmount.toString()

            itemView.setOnClickListener{
                var intent = Intent(mContext, OrderDetailActivity::class.java)


                intent.putExtra("orderid", orders._id)

                mContext.startActivity(intent)
            }

        }


    }



}