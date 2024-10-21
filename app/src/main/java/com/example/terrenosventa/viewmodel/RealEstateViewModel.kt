package com.example.terrenosventa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.terrenosventa.data.RealEstateDatabase
import com.example.terrenosventa.model.RealEstate
import com.example.terrenosventa.repository.RealEstateRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RealEstateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RealEstateRepository
    val realEstateList: StateFlow<List<RealEstate>>

    init {
        val realEstateDao = RealEstateDatabase.getDatabase(application).realEstateDao()
        repository = RealEstateRepository(realEstateDao)

        realEstateList = repository.allRealEstates
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshRealEstates()
            } catch (e: Exception) {
               }
        }
    }
}
