package com.example.terrenosventa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.terrenosventa.model.RealEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(realEstates: List<RealEstate>)

    @Query("SELECT * FROM real_estate_table ORDER BY id ASC")
    fun getAllRealEstates(): Flow<List<RealEstate>>
}
