package com.morse.data.repository

import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.data.SuperHeroMarvelItemDetail
import com.morse.domain.repository.MarvelHerosRepositoryInterface
import com.morse.domain.state.NetworkErrorType
import com.morse.domain.state.NetworkState
import com.morse.remote.core.RetrofitBuilder
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MarvelHerosRepositoryImpl : MarvelHerosRepositoryInterface {

    public override fun laodSuperHeros () : Observable<NetworkState<SuperHeroMarvelItem>>{
        return Observable.create {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    var result = RetrofitBuilder.getNetworkInteractor().getMarvelHeros()?.await()
                    var convertedResult = result?.map {
                        SuperHeroMarvelItem(it?.color , it?.details?.map {
                            SuperHeroMarvelItemDetail(it?.id , it?.name , it?.plot , it?.poster , it?.rootId)
                        } , it?.id , it?.name , it?.poster , it?.quote)
                    } as ArrayList
                    it?.onNext(NetworkState.success<SuperHeroMarvelItem>(convertedResult))
                }catch (e: Exception){
                    if (e?.localizedMessage?.contains("Unable to resolve host name")!!){
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