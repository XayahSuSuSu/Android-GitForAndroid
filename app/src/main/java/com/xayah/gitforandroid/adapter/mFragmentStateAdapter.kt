package com.xayah.gitforandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class mFragmentStateAdapter(fragmentActivity: FragmentActivity, fragments: MutableList<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments: MutableList<Fragment>

    init {
        this.fragments = fragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
