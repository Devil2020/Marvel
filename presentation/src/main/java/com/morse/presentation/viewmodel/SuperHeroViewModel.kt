package com.morse.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.state.NetworkErrorType
import com.morse.domain.state.NetworkState
import com.morse.domain.usecases.GetSuperHerosInformations
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultErrorState
import com.morse.presentation.state.ResultState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class SuperHeroViewModel constructor( private var useCase : GetSuperHerosInformations) : ViewModel() {

    public var superHerosResult = MutableLiveData<ResultState<PresentationSuperHeroItem>>()
    public var compositeDisposable = CompositeDisposable ()

    init {
        loadSuperHeros()
    }

public fun loadSuperHeros () {
    superHerosResult?.postValue(ResultState.isLoading<PresentationSuperHeroItem>(true))
    useCase?.loadIt()
        ?.observeOn(Schedulers.io())?.map {
            if ((it is NetworkState.success<SuperHeroMarvelItem>)) {
                ResultState.success<PresentationSuperHeroItem>(
                    it?.result?.map {
                        PresentationSuperHeroItem(it?.color, it?.details?.map {
                            PresentationSuperHeroDetail(
                                it?.id,
                                it?.name,
                                it?.plot,
                                it?.poster,
                                it?.rootId
                            )
                        }, it?.id, it?.name, it?.poster, it?.quote)
                    } as ArrayList<PresentationSuperHeroItem>)
            } else {
                if ((it as NetworkState.error<SuperHeroMarvelItem>)?.errorType == NetworkErrorType.TIME_OUT) {
                    ResultState.error<PresentationSuperHeroItem>(ResultErrorState.TIME_OUT)
                } else {
                    ResultState.error<PresentationSuperHeroItem>(ResultErrorState.NO_INTERNET)
                }

            }
        }?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
            superHerosResult?.postValue(it)
        }?.addTo(compositeDisposable)
}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }

}