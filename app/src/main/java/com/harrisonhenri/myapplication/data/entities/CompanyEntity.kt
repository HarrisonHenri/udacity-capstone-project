package com.harrisonhenri.myapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class CompanyEntity(@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val address: String, val imageUrl: String, val menuId: Int, val geoLat: Double, val geoLon: Double)
