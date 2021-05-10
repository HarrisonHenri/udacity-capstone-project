package com.harrisonhenri.myapplication.repository.models

data class Company(val id: Int, val name: String, val address: String, val imageUrl: String, val menuId: Int, val geoLat: Double, val geoLon: Double)