package com.nasa.apod.common.util.imageLoader

import android.content.Context
import java.io.File

class FileCache(var context: Context) {

    private var cacheDir: File? = null

    fun getFile(url: String): File {
        val filename = url.hashCode().toString()
        return File(cacheDir, filename)
    }

    init {
        cacheDir = context.cacheDir
        if (!cacheDir?.exists()!!) cacheDir?.mkdirs()
    }
}