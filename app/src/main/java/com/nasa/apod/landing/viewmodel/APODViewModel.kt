package com.nasa.apod.landing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa.apod.landing.data.APODApiService
import com.nasa.apod.landing.model.APODModel
import com.nasa.apod.landing.repository.APODRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class APODViewModel @Inject constructor(
    private val repository: APODRepository,
    private val apiService: APODApiService
) : ViewModel() {

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    val liveData: LiveData<APODModel> by lazy { MutableLiveData() }
    val error: LiveData<String> by lazy { MutableLiveData() }

    fun fetchAPODFromApi() {
        mCompositeDisposable.add(Single.just(true)
            .map { repository.fetchAPODResponse(apiService) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    (liveData as MutableLiveData<APODModel>).value = it
                }, {
                    (error as MutableLiveData<String>).value = it.localizedMessage
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }
}