package com.morse.ui.ui.detail.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.R
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter
import com.morse.ui.ui.detail.listener.SeriesMoviesListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.series_super_hero_templete.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() , SeriesMoviesListener {

    var seriesAdapter = SeriousMovieAdapter(this)
    var superHero = PresentationSuperHeroItem()
    lateinit var ourView : View
    var count = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        superHero = intent?.getParcelableExtra<PresentationSuperHeroItem>("superHeroData")!!
        renderViews()
        registerActions()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerActions (){

    card?.setOnClickListener {
        android.transition.TransitionManager.beginDelayedTransition(
            detailRoot,
            getTransform(card, ourView)
        )
        card?.isGone = true
        ourView?.isGone = false
    }

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

    private fun renderDetail (detail : PresentationSuperHeroDetail){

        Glide.with(card!!).asGif().load(R.drawable.spinner_loading).transform(
            RoundedCorners(30)
        ).into(seriesMovieImageLoading)

        Picasso.get().load(detail?.poster)
            .transform(RoundedCornersTransformation(30 , 0))
            .into(seriesMovieImage , object : Callback {
                override fun onSuccess() {
                    seriesMovieImageLoading?.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {
                    seriesMovieImageLoading?.visibility = View.INVISIBLE
                }
            })

        movieName?.text = detail?.name
        movieDetail?.text = detail?.plot
    }

    private fun renderViews (){
        Glide.with(detailRoot!!).asGif().load(R.drawable.spinner_loading).transform(
            RoundedCorners(25)
        ).into(loading)

        Picasso.get().load(superHero?.poster)
            .transform(RoundedCornersTransformation(25 , 0))
            .into(detailSuperHeroImage , object : Callback {
                override fun onSuccess() {
                    loading?.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {
                    loading?.visibility = View.INVISIBLE
                }
            })

        seriousRecyclerview?.adapter = seriesAdapter
        seriesAdapter?.submitSerious(superHero?.details as ArrayList<PresentationSuperHeroDetail>)
        heroNameDetail?.text = superHero?.quote
        heroName?.text = superHero?.name
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSeriesClicked(view: View, detail: PresentationSuperHeroDetail) {
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
        renderDetail(detail)
        count ++
    }

}