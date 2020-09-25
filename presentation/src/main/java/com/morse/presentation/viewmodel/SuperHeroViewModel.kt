package com.morse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.state.NetworkState
import com.morse.domain.usecases.GetSuperHerosInformations
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem


class SuperHeroViewModel constructor( private var useCase : GetSuperHerosInformations) : ViewModel() {

public fun loadSuperHeros () = useCase?.loadIt()?.map {
    (it as NetworkState.success<SuperHeroMarvelItem>)?.result?.map {
        PresentationSuperHeroItem(it?.color , it?.details?.map {
            PresentationSuperHeroDetail(it?.id , it?.name , it?.plot ,it?.poster , it?.rootId )
        } , it?.id , it?.name , it?.poster , it?.quote)
    }
}



}