package com.example.terminalfinance.data

import retrofit2.http.GET

interface ApiService {

    @GET("https://api.polygon.io/v2/aggs/ticker/AAPL/range/1/hour/2022-01-09/2023-01-09?adjusted=true&sort=desc&limit=5000&apiKey=C3Vr1rggHMfbx3mjRbBjQglNUJsdZqGL")
    suspend fun loadBars(): Result
}