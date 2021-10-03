package com.nasa.apod.common.receiver

import android.content.Context
import android.net.*
import android.os.Build

fun isNetworkAvailable(context: Context): Boolean? {
    var isConnected: Boolean? = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                isConnected = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        true
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        true
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    } else {
        @Suppress("DEPRECATION")
        cm?.run {
            cm.activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        true
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        true
                    }
                    ConnectivityManager.TYPE_VPN -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
    return isConnected
}

class NetworkReceiver {

    interface NetworkChangeListener {
        fun onNetworkConnected()
        fun onNetworkDisconnected()
    }

    private lateinit var listener: NetworkChangeListener

    private val networkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                listener.onNetworkConnected()
            }

            override fun onLost(network: Network) {
                listener.onNetworkDisconnected()
            }
        }

    fun registerReceiver(context: Context?, listener: NetworkChangeListener) {
        this.listener = listener
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(networkCallback)
            } else {
                val request = NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build()
                it.registerNetworkCallback(request, networkCallback)
            }
        }
    }

    fun unregisterReceiver(context: Context?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}