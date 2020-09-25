package com.morse.domain.repository
import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.state.NetworkState
import io.reactivex.Observable

interface MarvelHerosRepositoryInterface {

    public fun laodSuperHeros () : Observable<NetworkState<SuperHeroMarvelItem>>

}