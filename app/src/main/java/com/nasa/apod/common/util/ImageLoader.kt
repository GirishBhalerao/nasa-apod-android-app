package com.nasa.apod.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.nasa.apod.common.util.imageLoader.FileCache
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ImageLoader(var context: Context) {

    interface ImageLoadingListener {
        fun onImageLoadSuccess(bitmap: Bitmap?)
    }

    private val fileCache: FileCache = FileCache(context)
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun display(listener: ImageLoadingListener, url: String?) {
        var bmp: Bitmap? = null
        executor.execute {
            try {
                bmp = url?.let { getBitmapFromURL(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            handler.post {
                listener.onImageLoadSuccess(bmp)
            }
        }
    }

    private fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val f: File = fileCache.getFile(src)
            if (f.exists()) {
                val b: Bitmap? = BitmapFactory.decodeStream(f.inputStream())
                if (b != null) return b
            }
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val os: OutputStream = FileOutputStream(f)
            FileHelper.copyStream(input, os)
            val bitmap = BitmapFactory.decodeStream(f.inputStream())
            input.close()
            os.close()
            return bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}