package com.xayah.gitforandroid.fragment.repo

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.xayah.gitforandroid.R
import com.xayah.gitforandroid.adapter.mFragmentStateAdapter
import com.xayah.gitforandroid.databinding.DialogAddRepoBinding
import com.xayah.gitforandroid.fragment.add.AddFragment


class RepoViewModel : ViewModel() {
    fun onCloneDialog(v: View) {
        var context = v.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                break
            }
            context = context.baseContext
        }
        val binding =
            DialogAddRepoBinding.inflate((context as Activity).layoutInflater)
        val fragments = mutableListOf<Fragment>()
        val create = AddFragment()
        create.created = {
            it.textLayoutUp.hint = context.getString(R.string.name)
            it.textLayoutDown.hint = context.getString(R.string.path)
        }
        val import = AddFragment()
        import.created = {
            it.textLayoutUp.hint = context.getString(R.string.name)
            it.textLayoutDown.hint = context.getString(R.string.path)
        }
        val clone = AddFragment()
        clone.created = {
            it.textLayoutUp.hint = context.getString(R.string.url)
            it.textLayoutDown.hint = context.getString(R.string.path)
            it.textFieldUp.isEnabled = true
            it.textFieldDown.isEnabled = true
        }
        fragments.add(create)
        fragments.add(import)
        fragments.add(clone)
        binding.viewPager2.adapter = mFragmentStateAdapter(context as FragmentActivity, fragments)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager2.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.add_repo))
            .setView(binding.root)
            .setCancelable(true)
            .setPositiveButton(context.getString(R.string.confirm), null)
            .show()
    }
}