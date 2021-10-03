package com.nasa.apod.landing.data

import com.nasa.apod.landing.model.APODModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APODApiService {

    @GET("{endpoint}")
    fun getAPODResponse(
        @Path("endpoint", encoded = true) endpoint: String,
        @Query("api_key", encoded = true) apiKey: String
    ): Call<APODModel>
}