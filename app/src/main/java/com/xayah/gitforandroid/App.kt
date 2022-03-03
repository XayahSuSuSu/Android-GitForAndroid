package com.xayah.gitforandroid

import android.app.Application
import android.content.Context
import com.topjohnwu.superuser.Shell
import com.xayah.gitforandroid.util.Path


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
            shell.newJob()
                .add("export HOME=${Path.getExternalFilesDir(context)}")
                .exec()
            return true
        }
    }
}