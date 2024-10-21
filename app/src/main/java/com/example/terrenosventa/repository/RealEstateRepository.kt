package com.example.terrenosventa.repository

import com.example.terrenosventa.data.RealEstateDao
import com.example.terrenosventa.model.RealEstate
import com.example.terrenosventa.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class RealEstateRepository(private val realEstateDao: RealEstateDao) {

    val allRealEstates: Flow<List<RealEstate>> = realEstateDao.getAllRealEstates()

    suspend fun refreshRealEstates() {
        val realEstateList = RetrofitInstance.api.getRealEstateList()
        realEstateDao.insertAll(realEstateList)
    }
}
