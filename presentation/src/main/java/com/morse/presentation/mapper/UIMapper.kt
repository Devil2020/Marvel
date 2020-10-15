package com.morse.presentation.mapper

import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.state.NetworkErrorType
import com.morse.domain.state.NetworkState
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultErrorState
import com.morse.presentation.state.ResultState

class UIMapper : ResultMapper<NetworkState<SuperHeroMarvelItem> , ResultState<PresentationSuperHeroItem>> {

    override fun performMap(input: NetworkState<SuperHeroMarvelItem>): ResultState<PresentationSuperHeroItem> {
        if ((input is NetworkState.success<SuperHeroMarvelItem>)) {
            return ResultState.success<PresentationSuperHeroItem>(
                input?.result?.map {
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
        }
        else {
            if ((input as NetworkState.error<SuperHeroMarvelItem>)?.errorType == NetworkErrorType.TIME_OUT) {
                return ResultState.error<PresentationSuperHeroItem>(ResultErrorState.TIME_OUT)
            } else {
                return ResultState.error<PresentationSuperHeroItem>(ResultErrorState.NO_INTERNET)
            }

        }
    }

}