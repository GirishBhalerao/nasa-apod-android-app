package com.nasa.apod.common.configuration

data class ApiConfig(var host: String, var aPodConfig: APODConfig)

data class APODConfig(var endpoint: String, var apiKey: String)