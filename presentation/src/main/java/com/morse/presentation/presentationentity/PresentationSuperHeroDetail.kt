package com.morse.presentation.presentationentity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresentationSuperHeroDetail (
    val id: Int ?=0,
    val name: String ?="",
    val plot: String ?="",
    val poster: String ?="",
    val rootId: Int ?=0
)  :Parcelable