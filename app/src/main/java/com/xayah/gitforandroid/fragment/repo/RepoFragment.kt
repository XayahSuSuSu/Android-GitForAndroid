package com.xayah.gitforandroid.fragment.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xayah.gitforandroid.databinding.FragmentRepoBinding
import com.xayah.materialyoufileexplorer.MaterialYouFileExplorer

class RepoFragment : Fragment() {

    private var _binding: FragmentRepoBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: RepoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RepoViewModel::class.java]
        viewModel.dirExplorer = MaterialYouFileExplorer()
        viewModel.dirExplorer.initialize(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}