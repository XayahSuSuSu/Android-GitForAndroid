package com.xayah.gitforandroid.fragment.home

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.topjohnwu.superuser.Shell
import com.topjohnwu.superuser.ShellUtils
import com.xayah.gitforandroid.R
import com.xayah.gitforandroid.databinding.DialogGitconfigBinding

class HomeViewModel : ViewModel() {
    var gitVersion: ObservableField<String> = ObservableField(getVersion())
    var gitConfig: ObservableField<String> =
        ObservableField(getConfig())

    fun onConfigDialog(v: View) {
        val context = v.context
        val binding = DialogGitconfigBinding.inflate((context as Activity).layoutInflater)
        binding.textFieldName.setText(ShellUtils.fastCmd("git config user.name"))
        binding.textFieldEmail.setText(ShellUtils.fastCmd("git config user.email"))
        binding.textFieldName.doAfterTextChanged {
            if (it?.isEmpty() == true) {
                binding.textLayoutName.error = context.getString(R.string.type_name)
            } else {
                binding.textLayoutName.error = null
            }
        }
        binding.textFieldEmail.doAfterTextChanged {
            if (it?.isEmpty() == true) {
                binding.textLayoutEmail.error = context.getString(R.string.type_email)
            } else {
                binding.textLayoutEmail.error = null
            }
        }
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.git_config))
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.confirm), null)
            .show()
        builder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (binding.textLayoutName.error == null && binding.textLayoutEmail.error == null) {
                Shell.cmd("git config --global user.name \"${binding.textFieldName.text}\"").exec()
                Shell.cmd("git config --global user.email ${binding.textFieldEmail.text}").exec()
                gitConfig.set(getConfig())
                builder.dismiss()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.check_config),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getVersion(): String {
        return ShellUtils.fastCmd("git --version").replace("git version ", "")
    }

    fun updateVersion() {
        gitVersion.set(getVersion())
    }

    private fun getConfig(): String {
        val name = ShellUtils.fastCmd("git config user.name")
        val email = ShellUtils.fastCmd("git config user.email")
        val config = "$name <${email}>"
        return if (email.isEmpty()) "Not configured" else config
    }
}