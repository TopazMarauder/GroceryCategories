package com.example.groceryappb30.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappb30.R
import com.example.groceryappb30.activities.SubCategoryActivity
import com.example.groceryappb30.app.Config
import com.example.groceryappb30.databinding.AdapterCategoryBinding
import com.example.groceryappb30.models.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(var mContext: Context) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var mList: ArrayList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view =
            LayoutInflater.from(mContext).inflate(R.layout.adapter_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    fun setData(list: ArrayList<Category>){
        mList = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: AdapterCategoryBinding


        fun bind(category: Category) {
            binding = AdapterCategoryBinding.bind(itemView)
            binding.textViewCategoryName.text = category.catName

            Picasso.get()
                .load("${Config.IMAGE_URL+ category.catImage}")
                .into(binding.imageViewCategory)


            itemView.setOnClickListener{
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra(Category.KEY_CATEGORY, category)
                mContext.startActivity(intent)


            }
        }

    }

}

