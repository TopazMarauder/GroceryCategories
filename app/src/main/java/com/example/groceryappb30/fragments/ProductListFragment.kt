package com.example.groceryappb30.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappb30.adapters.ProductAdapter
import com.example.groceryappb30.app.Endpoints
import com.example.groceryappb30.databinding.FragmentSubCategoryBinding
import com.example.groceryappb30.models.PageViewModel
import com.example.groceryappb30.models.Product
import com.example.groceryappb30.models.ProductResponse
import com.example.groceryappb30.models.Subcategory
import com.google.gson.Gson



class ProductListFragment : Fragment() {

    private var _binding: FragmentSubCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageViewModel: PageViewModel

    lateinit var productAdapter: ProductAdapter

    var mContext: Context? = null

    private var subId: Int? = 1

    var productList: ArrayList<Product> = ArrayList()



 /*   override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            subId = it.getInt(Subcategory.KEY_SUBCATEGORY)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        init(view)
        return view
    }

    private fun init(view: View) {

        generateLists()
        //Toast.makeText(mContext, "why no work", Toast.LENGTH_SHORT).show()
        productAdapter = ProductAdapter(activity!!)
        binding.recyclerViewSub.adapter = productAdapter
        binding.recyclerViewSub.layoutManager = GridLayoutManager(activity!!, 2)
        binding.recyclerViewSub.addItemDecoration(DividerItemDecoration(activity!!, GridLayoutManager.HORIZONTAL))
        binding.recyclerViewSub.addItemDecoration(DividerItemDecoration(activity!!, GridLayoutManager.VERTICAL))
    }

    private fun generateLists() {
        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProduct(subId!! /*replace this*/),
            Response.Listener<String> {
                var gson = Gson()
                var productResponse = gson.fromJson(it, ProductResponse::class.java)
                productAdapter.setData(productResponse.data)

            }
            ,
            Response.ErrorListener {

            }
        )

        requestQueue.add(request)
    }


    companion object {

        @JvmStatic
        fun newInstance(subId: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(Subcategory.KEY_SUBCATEGORY, subId)
                }
            }
    }



}
