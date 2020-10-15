package com.morse.data.repository

import com.morse.data.mapper.DataMapper
import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.data.SuperHeroMarvelItemDetail
import com.morse.domain.repository.MarvelHerosRepositoryInterface
import com.morse.domain.state.NetworkErrorType
import com.morse.domain.state.NetworkState
import com.morse.remote.core.RetrofitBuilder
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MarvelHerosRepositoryImpl : MarvelHerosRepositoryInterface {

    var dataMapper = DataMapper()

    public override fun laodSuperHeros () : Observable<NetworkState<SuperHeroMarvelItem>>{
        return Observable.create {
            var emitter = it
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    var result = RetrofitBuilder.getNetworkInteractor().getMarvelHeros()?.await()
                        var convertedResult = result?.map {
                            dataMapper?.performMap(it)
                        } as ArrayList
                        emitter ?.onNext(NetworkState.success<SuperHeroMarvelItem>(convertedResult))

                }catch (e: Exception){
                    if (e?.localizedMessage?.contains("Unable to resolve host")!!){
                        it?.onNext(NetworkState.error<SuperHeroMarvelItem>(NetworkErrorType.NO_INTERNET))
                    }
                    else {
                        it?.onNext(NetworkState.error<SuperHeroMarvelItem>(NetworkErrorType.TIME_OUT))
                     }
                    }
                }
            }
        }
    }


