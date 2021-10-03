package com.nasa.apod.landing.repository

import com.nasa.apod.landing.data.APODApiService
import com.nasa.apod.landing.model.APODModel

interface APODRepository {
    fun fetchAPODResponse(aPodApiService: APODApiService): APODModel?
}