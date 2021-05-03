package com.example.groceryappb30.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappb30.R
import com.example.groceryappb30.activities.CartActivity
import com.example.groceryappb30.activities.CategoryActivity
import com.example.groceryappb30.activities.ProductActivity
import com.example.groceryappb30.activities.SubCategoryActivity
import com.example.groceryappb30.app.Config
import com.example.groceryappb30.database.DBHelper
import com.example.groceryappb30.databinding.ActivityCartBinding
import com.example.groceryappb30.databinding.AdapterCartBinding
import com.example.groceryappb30.models.Category
import com.example.groceryappb30.models.Product
import com.squareup.picasso.Picasso

class CartAdapter(var mContext: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    var mList: ArrayList<Product> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    fun setData(list: ArrayList<Product>){
        mList = list
        notifyDataSetChanged()

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: AdapterCartBinding
        lateinit var dbHelper: DBHelper



        fun bind(product: Product) {
            dbHelper = DBHelper(mContext)

            binding = AdapterCartBinding.bind(itemView)
            binding.textViewProductName.text = product.productName
            binding.textViewProductUnit.text = product.unit
            binding.textViewProductPrice.text = product.price.toString()
            binding.textViewItemCount.text = product.quantity.toString()//dbHelper.getQuantityById(product._id).toString()


            Picasso.get()
                .load("${Config.IMAGE_URL + product.image}")
                .placeholder(R.drawable.ic_baseline_clear_24)
                .into(binding.imageViewProduct)



            binding.buttonAdd.setOnClickListener{
                dbHelper.incrementProduct(product)
                product.quantity ++
                binding.textViewItemCount.text = product.quantity.toString()

                (mContext as CartActivity).updatePrice(dbHelper.getPrices())
            }
            binding.buttonRemove.setOnClickListener {
                var (toss, use) = dbHelper.decrementProduct(product)
                if (!use){

                    var productList = dbHelper.getAllProducts()

                    setData(productList)

                    (mContext as CartActivity).updatePrice(dbHelper.getPrices())
                    //var intent = Intent(mContext, CategoryActivity::class.java)
                    //mContext.startActivity(intent)
                }
                else{
                    product.quantity --
                    binding.textViewItemCount.text = product.quantity.toString()


                    (mContext as CartActivity).updatePrice(dbHelper.getPrices())
                }


            }
            binding.buttonDelete.setOnClickListener {
                dbHelper.deleteProduct(product._id)
                var productList = dbHelper.getAllProducts()

                setData(productList)

                (mContext as CartActivity).updatePrice(dbHelper.getPrices())

            }

        }








    }
}