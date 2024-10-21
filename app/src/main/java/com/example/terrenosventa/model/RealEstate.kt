package com.example.terrenosventa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "real_estate_table")
data class RealEstate(
    val price: Int,
    @PrimaryKey val id: Int,
    val type: String,
    val img_src: String
)
