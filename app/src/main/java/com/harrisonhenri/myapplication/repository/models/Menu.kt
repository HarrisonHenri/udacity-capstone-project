package com.harrisonhenri.myapplication.repository.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class Menu(val id: Int, val categoriesList: @RawValue List<Category>): Parcelable