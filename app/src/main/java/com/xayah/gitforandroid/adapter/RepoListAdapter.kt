package com.xayah.gitforandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.xayah.gitforandroid.databinding.AdapterRepoListBinding
import com.xayah.gitforandroid.model.RepoList

class RepoListAdapter : ItemViewDelegate<RepoList, RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            AdapterRepoListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RepoList) {
        val binding = holder.binding
        val adapter = MultiTypeAdapter()
        val items = ArrayList<Any>()
        adapter.register(RepoAdapter())
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(binding.root.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = layoutManager
        for (i in item.repo) {
            items.add(i)
        }
        adapter.items = items
    }

    class ViewHolder(val binding: AdapterRepoListBinding) : RecyclerView.ViewHolder(binding.root)
}