package com.example.groceryappb30.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappb30.R
import com.example.groceryappb30.activities.ProductActivity
import com.example.groceryappb30.app.Config
import com.example.groceryappb30.databinding.AdapterProductBinding
import com.example.groceryappb30.models.Product
import com.squareup.picasso.Picasso

class ProductAdapter(var mContext: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {


    var mList: ArrayList<Product> = ArrayList()
    var whereImplemented: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.adapter_product, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return mList.size

    }

    fun setData(list: ArrayList<Product>, whereImplement: Int = 0) {
        mList = list
        whereImplemented = whereImplement
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: AdapterProductBinding
        fun bind(product: Product) {
            binding = AdapterProductBinding.bind(itemView)

            if (whereImplemented == 1){
                binding.textViewProductName.text = product.quantity.toString() + " x " + product.productName
            }
            else{
                binding.textViewProductName.text = product.productName
            }

            Picasso.get()
                .load("${Config.IMAGE_URL + product.image}")
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageViewProduct)


            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductActivity::class.java)
                intent.putExtra(Product.KEY_PRODUCT, product)
                mContext.startActivity(intent)

            }


        }
    }
}