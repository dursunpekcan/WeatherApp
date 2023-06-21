package com.nackep.wheatherapp.model

data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)