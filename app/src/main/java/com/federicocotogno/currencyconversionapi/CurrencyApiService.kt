package com.federicocotogno.currencyconversionapi
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {
    @GET("v6/{apiKey}/latest/{baseCurrency}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String
    ): ApiResponse

    @GET("v6/{apiKey}/history/{baseCurrency}/{date}")
    suspend fun getHistoricalRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String,
        @Path("date") date: String
    ): ApiResponse
}

data class ApiResponse(
    val conversion_rates: Map<String, Double>
)
