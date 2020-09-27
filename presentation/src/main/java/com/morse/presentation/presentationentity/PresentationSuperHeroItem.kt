package com.morse.presentation.presentationentity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresentationSuperHeroItem (
    val color: String?="",
    val details: List<PresentationSuperHeroDetail>?=ArrayList<PresentationSuperHeroDetail>(),
    val id: Int?=0,
    val name: String?="",
    val poster: String?="",
    val quote: String?=""
) : Parcelable