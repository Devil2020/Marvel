package com.morse.lib.entity

public data class MarvelHerosItem(
    val color: String?="",
    val details: List<Detail>?=ArrayList<Detail>(),
    val id: Int?=0,
    val name: String?="",
    val poster: String?="",
    val quote: String?=""
)