package com.nasa.apod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.nasa.apod.common.util.launchFragment
import com.nasa.apod.landing.fragment.APODFragment
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        clearBackStack()
        val fragment = APODFragment()
        launchFragment(
            R.id.cnsContainer,
            supportFragmentManager,
            fragment,
            APODFragment::class.simpleName
        )
    }

    private fun clearBackStack() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        clearBackStack()
        super.onBackPressed()
    }
}