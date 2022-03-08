package com.xayah.gitforandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.xayah.gitforandroid.databinding.AdapterRepoNameBinding

class RepoNameAdapter : ItemViewDelegate<String, RepoNameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            AdapterRepoNameBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        val binding = holder.binding
        binding.materialTextView.text = item
    }

    class ViewHolder(val binding: AdapterRepoNameBinding) : RecyclerView.ViewHolder(binding.root)
}