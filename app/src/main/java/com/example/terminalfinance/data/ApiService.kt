package com.example.terminalfinance.data

import com.example.terminalfinance.presentation.TimeFrame
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("https://api.polygon.io/v2/aggs/ticker/AAPL/range/{timeframe}/2022-01-09/2023-01-09?adjusted=true&sort=desc&limit=5000&apiKey=C3Vr1rggHMfbx3mjRbBjQglNUJsdZqGL")
    suspend fun loadBars(
        @Path("timeframe") timeFrame: String
    ): Result
}