package com.xayah.gitforandroid

import android.app.Application
import android.content.Context
import com.topjohnwu.superuser.Shell
import com.xayah.gitforandroid.util.Command
import com.xayah.gitforandroid.util.Path
import com.xayah.gitforandroid.util.Tool


class App : Application() {
    companion object {
        init {
            Shell.enableVerboseLogging = BuildConfig.DEBUG
            Shell.setDefaultBuilder(
                Shell.Builder.create()
                    .setFlags(Shell.FLAG_REDIRECT_STDERR or Shell.FLAG_NON_ROOT_SHELL)
                    .setTimeout(10)
                    .setInitializers(EnvInitializer::class.java)
            )
        }
    }

    class EnvInitializer : Shell.Initializer() {
        override fun onInit(context: Context, shell: Shell): Boolean {
            if (!Command.ls("${Path.getExternalFilesDir(context)}/bin/git")) {
                Tool.extractAssets(context, "zstd")
                Tool.extractAssets(context, "git.zip.zst")
                Shell.cmd("chmod 777 ${Path.getExternalFilesDir(context)}/zstd").exec()
                Shell.cmd("cd ${Path.getExternalFilesDir(context)}; ./zstd -d git.zip.zst").exec()
                Command.unzip(
                    "${Path.getExternalFilesDir(context)}/git.zip",
                    Path.getExternalFilesDir(context)
                )
                Command.rm("${Path.getExternalFilesDir(context)}/git.zip")
            }
            shell.newJob()
                .add("export HOME=${Path.getExternalFilesDir(context)}")
                .add("export PATH=${Path.getExternalFilesDir(context)}/bin:\$PATH")
                .exec()
            return true
        }
    }

    override fun onCreate() {
        super.onCreate()
        Shell.getShell { }
    }
}