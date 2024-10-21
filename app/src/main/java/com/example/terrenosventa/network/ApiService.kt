package com.example.terrenosventa.network

import com.example.terrenosventa.model.RealEstate
import com.example.terrenosventa.util.Constants
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.ENDPOINT)
    suspend fun getRealEstateList(): List<RealEstate>
}
