package com.morse.ui.ui.detail.listener

import com.morse.presentation.presentationentity.PresentationSuperHeroDetail

interface AnimationListener {

    public fun onCardClicked ()

    public fun onSeriesRecommendationClicked (detail: PresentationSuperHeroDetail)

}