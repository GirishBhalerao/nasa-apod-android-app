package com.nasa.apod.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nasa.apod.common.receiver.NetworkReceiver

abstract class BaseFragment : Fragment(), NetworkReceiver.NetworkChangeListener {

    private lateinit var mNetworkReceiver: NetworkReceiver

    protected abstract fun onCreateView(): Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(onCreateView(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mNetworkReceiver = NetworkReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNetworkReceiver.registerReceiver(context, this)
    }

    override fun onDestroy() {
        mNetworkReceiver.unregisterReceiver(context)
        super.onDestroy()
    }

    abstract override fun onNetworkConnected()

    abstract override fun onNetworkDisconnected()
}