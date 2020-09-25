package com.morse.domain.data

data class SuperHeroMarvelItem (
    val color: String?="",
    val details: List<SuperHeroMarvelItemDetail>?=ArrayList<SuperHeroMarvelItemDetail>(),
    val id: Int?=0,
    val name: String?="",
    val poster: String?="",
    val quote: String?=""
)