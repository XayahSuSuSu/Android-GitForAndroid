package com.xayah.gitforandroid.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.topjohnwu.superuser.Shell
import com.xayah.gitforandroid.R
import com.xayah.gitforandroid.databinding.FragmentHomeBinding
import com.xayah.gitforandroid.util.Command
import com.xayah.gitforandroid.util.Path
import com.xayah.gitforandroid.util.Tool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        extractAssets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun extractAssets() {
        val context = requireContext()
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.wait))
            .setCancelable(false)
            .show()
        val mParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mParam.setMargins(0, 200, 0, 100)
        builder.addContentView(ProgressBar(context), mParam)
        val params: WindowManager.LayoutParams = builder.window!!.attributes
        builder.window!!.attributes = params
        CoroutineScope(Dispatchers.IO).launch {
            if (!Command.ls("${Path.getExternalFilesDir(context)}/bin/git")) {
                Tool.extractAssets(context, "zstd")
                Tool.extractAssets(context, "git.zip.zst")
                Shell.cmd("chmod 777 ${Path.getExternalFilesDir(context)}/zstd").exec()
                Shell.cmd("cd ${Path.getExternalFilesDir(context)}; ./zstd -d git.zip.zst")
                    .exec()
                Command.unzip(
                    "${Path.getExternalFilesDir(context)}/git.zip",
                    Path.getExternalFilesDir(context)
                )
                Command.rm("${Path.getExternalFilesDir(context)}/git.zip")
            }
            withContext(Dispatchers.Main) {
                builder.dismiss()
                viewModel.updateVersion()
            }
        }
    }
}