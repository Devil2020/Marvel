package com.morse.presentation.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morse.domain.usecases.GetSuperHerosInformations
import com.morse.presentation.mapper.UIMapper
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class SuperHeroViewModel constructor( private var useCase : GetSuperHerosInformations) : ViewModel() {

    private var compositeDisposable = CompositeDisposable ()
    public var superHerosResult = MutableLiveData<ResultState<PresentationSuperHeroItem>>()
    public var isLoading: ObservableBoolean = ObservableBoolean()
    public var isError: ObservableBoolean = ObservableBoolean()
    public var superHeros: ObservableArrayList<PresentationSuperHeroItem> =
        ObservableArrayList<PresentationSuperHeroItem>()
    private var uiMapper = UIMapper()

    init {
        loadSuperHeros()
    }

    public fun loadSuperHeros () {
    isLoading?.set(true)
    isError.set(false)
    useCase?.loadIt()
        ?.observeOn(Schedulers.io())
        ?.map {
            uiMapper?.performMap(it)
        }
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe {
            if (it is ResultState.error<PresentationSuperHeroItem>){
                isError.set(true)
            }
            else{
               isError.set(false)
                superHeros.addAll((it as ResultState.success<PresentationSuperHeroItem>)?.result)
            }
            superHerosResult?.postValue(it)
            isLoading?.set(false)
        }?.addTo(compositeDisposable)
}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }

}