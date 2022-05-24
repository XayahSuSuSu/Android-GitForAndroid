package com.xayah.gitforandroid.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xayah.gitforandroid.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    val binding get() = _binding!!

    var created: (binding: FragmentAddBinding) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        created(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getPath(): String {
        return "${binding.textFieldDown.text}/${binding.textFieldUp.text}"
    }
}