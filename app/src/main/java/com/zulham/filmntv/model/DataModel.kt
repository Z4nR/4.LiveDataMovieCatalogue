package com.zulham.filmntv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    val id: Int?,
    val title: String?,
    val releaseDate: String?,
    val vote: String?,
    val img: String?
): Parcelable