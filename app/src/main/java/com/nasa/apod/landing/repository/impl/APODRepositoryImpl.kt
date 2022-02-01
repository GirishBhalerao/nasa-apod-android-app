package com.nasa.apod.landing.repository.impl

import com.nasa.apod.common.configuration.ApiConfig
import com.nasa.apod.landing.data.APODApiService
import com.nasa.apod.landing.model.APODModel
import com.nasa.apod.landing.repository.APODRepository

class APODRepositoryImpl(private val apiConfig: ApiConfig) : APODRepository {

    override fun fetchAPODResponse(aPodApiService: APODApiService): APODModel? {
        return aPodApiService.getAPODResponse(
            apiConfig.aPodConfig.endpoint,
            apiConfig.aPodConfig.apiKey
        ).execute().body()
    }
}