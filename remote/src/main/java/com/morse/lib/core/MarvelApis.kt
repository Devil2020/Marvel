package com.morse.remote.core

import com.morse.lib.core.Retry
import com.morse.lib.entity.MarvelHerosItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

open interface MarvelApis {

    @Retry
    @GET(value = "/skydoves/ae1f687d7e67865a3d217ff719e256f8/raw/592160d562604476acd2d4adfd9d383058c7c558/MarvelLists.json")
    public fun getMarvelHeros () : Deferred<ArrayList< MarvelHerosItem>>

}