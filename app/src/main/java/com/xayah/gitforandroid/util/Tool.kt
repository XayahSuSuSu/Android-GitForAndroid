package com.xayah.gitforandroid.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Tool {
    companion object {
        fun extractAssets(mContext: Context, assetsName: String) {
            try {
                val assets = File(Path.getExternalFilesDir(mContext), assetsName)
                if (!assets.exists()) {
                    val outStream = FileOutputStream(assets)
                    val inputStream = mContext.resources.assets.open(assetsName)
                    inputStream.copyTo(outStream)
                    assets.setExecutable(true)
                    assets.setReadable(true)
                    assets.setWritable(true)
                    outStream.flush()
                    inputStream.close()
                    outStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}