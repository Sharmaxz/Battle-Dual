package com.infnet.battle_dual.creation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

open class CreationAdapter(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val fragments : MutableList<Fragment> = ArrayList()
    val titles : MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun addFragment(fragment : Fragment, title : String) {
        fragments.add(fragment)
        titles.add(title)
    }

}
