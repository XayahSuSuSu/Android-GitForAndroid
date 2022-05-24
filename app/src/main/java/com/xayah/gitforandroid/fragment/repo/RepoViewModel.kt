package com.xayah.gitforandroid.fragment.repo

import android.app.Activity
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.topjohnwu.superuser.Shell
import com.xayah.gitforandroid.R
import com.xayah.gitforandroid.adapter.mFragmentStateAdapter
import com.xayah.gitforandroid.databinding.DialogAddRepoBinding
import com.xayah.gitforandroid.fragment.add.AddFragment
import com.xayah.gitforandroid.model.room.RepoEntity
import com.xayah.gitforandroid.util.Room
import com.xayah.materialyoufileexplorer.MaterialYouFileExplorer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RepoViewModel : ViewModel() {
    lateinit var dirExplorer: MaterialYouFileExplorer

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
            it.textFieldDown.focusable = View.NOT_FOCUSABLE
            val that = it
            it.textFieldUp.doAfterTextChanged {
                that.textLayoutDown.helperText =
                    "Will locate at: \n${create.getPath()}"
            }
            it.textFieldDown.setOnClickListener {
                dirExplorer.toExplorer(
                    context,
                    false,
                    "Choose your repo directory"
                ) { path, _ ->
                    that.textFieldDown.setText(path)
                    that.textLayoutDown.helperText =
                        "Will locate at: \n${create.getPath()}"
                }
            }
        }
        val import = AddFragment()
        import.created = {
            it.textLayoutUp.hint = context.getString(R.string.name)
            it.textLayoutDown.hint = context.getString(R.string.path)
            it.textFieldUp.isEnabled = false
            it.textFieldDown.focusable = View.NOT_FOCUSABLE
        }
        val clone = AddFragment()
        clone.created = {
            it.textLayoutUp.hint = context.getString(R.string.url)
            it.textLayoutDown.hint = context.getString(R.string.path)
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
            .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                when (binding.viewPager2.currentItem) {
                    0 -> {
                        val x = Shell.cmd("git init ${create.getPath()}").exec()
                        Shell.cmd("cd ${create.getPath()}").exec()
                        Shell.cmd("echo 'fuck\nfuck' > ${create.getPath()}/.gitattributes").exec()
                        Shell.cmd("git add .").exec()
                        Shell.cmd("git commit -m 'Initial commit'").exec()
                        val y = Shell.cmd("git branch").exec()
                        Log.d("FUCK", x.out.toString())
                        Log.d("FUCK", y.out.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            val room = Room(context)
                            val repoEntity = RepoEntity(
                                0,
                                create.binding.textFieldUp.text.toString(),
                                "a",
                                create.getPath(),
                                "main"
                            )
                            room.insert(repoEntity)
                        }
                    }
                }
            }
            .show()
    }
}