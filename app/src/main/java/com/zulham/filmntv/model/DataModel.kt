package com.zulham.filmntv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    val title: String?,
    val releaseDate: String?,
    val genre: String?,
    val production: String?,
    val desc: String?,
    val img: String
): Parcelable