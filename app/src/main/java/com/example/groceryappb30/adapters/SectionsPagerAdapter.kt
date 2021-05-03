package com.example.groceryappb30.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryappb30.fragments.ProductListFragment
import com.example.groceryappb30.models.Subcategory


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {


    var mFragment: ArrayList<Fragment> = ArrayList()
    var mTitle: ArrayList<String> = ArrayList()


    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return mFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return mFragment.size
    }

    fun addFragment(subcategory: Subcategory){
        mTitle.add(subcategory.subName)
        mFragment.add(ProductListFragment.newInstance(subcategory.subId))
    }
}

