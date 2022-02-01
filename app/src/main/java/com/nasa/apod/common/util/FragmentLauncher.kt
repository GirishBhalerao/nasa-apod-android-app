package com.nasa.apod.common.util

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun launchFragment(
    containerId: Int?,
    fragmentManager: FragmentManager?,
    fragment: Fragment,
    tag: String?
) {
    if (containerId != null) {
        fragmentManager?.beginTransaction()?.apply {
            replace(containerId, fragment, tag)
            addToBackStack(tag)
            commit()
        }
    } else {
        Log.d("APOD - LaunchFragment", "Container Id is null")
    }
}