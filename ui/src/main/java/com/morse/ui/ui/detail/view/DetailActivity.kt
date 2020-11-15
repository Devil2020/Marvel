package com.morse.ui.ui.detail.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.transition.Slide
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.R
import com.morse.ui.databinding.ActivityDetailBinding
import com.morse.ui.databinding.ActivityHomeBinding
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter
import com.morse.ui.ui.detail.listener.AbstractListener
import com.morse.ui.ui.detail.listener.AnimationListener
import com.morse.ui.ui.detail.listener.SeriesMoviesListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.series_super_hero_templete.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() , SeriesMoviesListener , AbstractListener{

    var seriesAdapter = SeriousMovieAdapter(this)
    var superHero = PresentationSuperHeroItem()
    lateinit var ourView : View
    var count = 0
    lateinit var detailDataBinding : ActivityDetailBinding
    lateinit var animationListener: AnimationListener
    lateinit var bigPictureLoadedObservableBoolean: ObservableBoolean

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setupDataBinding()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupDataBinding (){
        detailDataBinding = DataBindingUtil.setContentView<ActivityDetailBinding>(this ,R.layout.activity_detail)
        superHero = intent?.getParcelableExtra<PresentationSuperHeroItem>("superHeroData")!!
        detailDataBinding?.superHero = ObservableArrayList<PresentationSuperHeroDetail>()?.apply {
            addAll(superHero?.details!!)
        }
        animationListener = object : AnimationListener {

            override fun onCardClicked() {
                android.transition.TransitionManager.beginDelayedTransition(
                    detailRoot,
                    getTransform(card, ourView)
                )
                card?.isGone = true
                ourView?.isGone = false
            }

            override fun onSeriesRecommendationClicked(detail: PresentationSuperHeroDetail) {
            }

        }
        detailDataBinding?.listener = animationListener
        seriousRecyclerview?.adapter = seriesAdapter
        bigPictureLoadedObservableBoolean = ObservableBoolean()
        detailDataBinding?.superHeroData = superHero
        detailDataBinding?.abstractListener = this
        seriesAdapter?.submitSerious(superHero?.details as ArrayList<PresentationSuperHeroDetail>)
    }

    override fun onBackPressed() {
        this.finish()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getTransform(mStartView: View, mEndView: View): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            startView = mStartView
            endView = mEndView
            addTarget(mEndView)
            pathMotion = MaterialArcMotion()
            duration = 550
            scrimColor = Color.TRANSPARENT
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public fun animateCard (view: View){
        if (count != 0) {
            if (ourView?.isGone == true) {

                ourView?.isGone = false
                card?.isGone = true
            }
        }
        ourView = view

        android.transition.TransitionManager.beginDelayedTransition(
            detailRoot,
            getTransform(ourView ,card )
        )
        ourView?.isGone = true
        card?.isGone = false
        count ++
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSeriesClicked(view: View, detail: PresentationSuperHeroDetail) {
        animateCard(view)
        detailDataBinding.superHeroDetail = detail

    }

    override fun navigateBack() {
        this.finish()
    }

}