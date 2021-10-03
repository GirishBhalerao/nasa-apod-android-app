package com.nasa.apod.landing.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.nasa.apod.R
import com.nasa.apod.base.BaseFragment
import com.nasa.apod.common.util.ImageLoader
import com.nasa.apod.landing.viewmodel.APODViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class APODFragment : BaseFragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: APODViewModel

    private lateinit var mNestedScrollView: NestedScrollView
    private lateinit var mPbrLoader: ProgressBar

    private var mIsError: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(APODViewModel::class.java)
    }

    override fun onCreateView(): Int {
        return R.layout.fragment_apod
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
        fetchAPODData(view)
    }

    private fun initUi(view: View) {
        mNestedScrollView = view.findViewById(R.id.scvContainer)
        mPbrLoader = view.findViewById(R.id.pbrLoader)
    }

    private fun fetchAPODData(view: View) {
        val txtViewDesc: TextView = view.findViewById(R.id.txtPODDesc)
        val txtViewDate: TextView = view.findViewById(R.id.txtPODDate)
        val imgViewAPOD: ImageView = view.findViewById(R.id.imgPOD)
        mViewModel.fetchAPODFromApi()
        mViewModel.liveData.observe(viewLifecycleOwner, {
            txtViewDesc.text = it.explanation
            val date = "${getString(R.string.apod_date_label)} ${it.date}"
            txtViewDate.text = date
            context?.let { it1 ->
                ImageLoader(it1).display(
                    object : ImageLoader.ImageLoadingListener {
                        override fun onImageLoadSuccess(bitmap: Bitmap?) {
                            imgViewAPOD.setImageBitmap(bitmap)
                            showProgressBar(false)
                        }
                    }, it.url
                )
            }
        })
        mViewModel.error.observe(viewLifecycleOwner, {
            mIsError = true
            showProgressBar(false)
            showSnackBar(view, getString(R.string.apod_api_failure_error))
        })
    }

    override fun onNetworkConnected() {
        if (mIsError) {
            mIsError = false
            view?.post {
                showProgressBar(true)
                mViewModel.fetchAPODFromApi()
            }
        }
    }

    override fun onNetworkDisconnected() {
        showSnackBar(view, getString(R.string.apod_network_error_label))
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            mNestedScrollView.visibility = View.GONE
            mPbrLoader.visibility = View.VISIBLE
        } else {
            mNestedScrollView.visibility = View.VISIBLE
            mPbrLoader.visibility = View.GONE
        }
    }

    private fun showSnackBar(view: View?, message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }
}