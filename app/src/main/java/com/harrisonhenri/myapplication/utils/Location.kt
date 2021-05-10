package com.harrisonhenri.myapplication.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.harrisonhenri.myapplication.repository.models.Distance
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun AppCompatActivity.isPermissionGranted() : Boolean {
    return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

fun getDistanceLatLonKm(companyLatLng: LatLng, userLatLong: LatLng?): Distance? {
    if (userLatLong == null){
        return Distance("")
    }
    val earthRadius = 6371;
    val dLat = deg2rad(companyLatLng.latitude - userLatLong.latitude)
    val dLon = deg2rad(companyLatLng.longitude - userLatLong.longitude)
    val calc = sin(dLat / 2) * sin(dLat / 2) +
            cos(deg2rad(userLatLong.latitude)) *
            cos(deg2rad(companyLatLng.latitude)) *
            sin(dLon / 2) *
            sin(dLon / 2)
    val c = 2 * atan2(sqrt(calc), sqrt(1 - calc))
    return Distance((earthRadius * c).format(2).toString().plus(" km"))
}

fun deg2rad(deg: Double): Double {
    return deg * (Math.PI / 180);
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)
