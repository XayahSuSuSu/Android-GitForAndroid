package com.xayah.gitforandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.xayah.gitforandroid.databinding.AdapterRepoBinding
import com.xayah.gitforandroid.model.Repo

class RepoAdapter : ItemViewDelegate<Repo, RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(AdapterRepoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Repo) {
        val binding = holder.binding
        binding.materialTextViewName.text = item.name
        binding.materialTextViewBranch.text = item.branch
    }

    class ViewHolder(val binding: AdapterRepoBinding) : RecyclerView.ViewHolder(binding.root)
}