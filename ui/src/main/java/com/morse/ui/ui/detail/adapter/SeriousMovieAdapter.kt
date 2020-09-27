package com.morse.ui.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.R
import com.morse.ui.ui.detail.listener.SeriesMoviesListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.series_super_hero_templete.*
import kotlinx.android.synthetic.main.super_hero_templete.*
import kotlinx.android.synthetic.main.super_hero_templete.loading
import java.lang.Exception

class SeriousMovieAdapter (private var listener : SeriesMoviesListener) : RecyclerView.Adapter<SeriousMovieAdapter.SeriousMovieViewHolder> () {

    private var listOfSeriousDetails = arrayListOf<PresentationSuperHeroDetail>()

    public fun submitSerious (data : ArrayList<PresentationSuperHeroDetail>){
        this?.listOfSeriousDetails?.clear()
        this?.listOfSeriousDetails?.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriousMovieViewHolder {
        return SeriousMovieViewHolder(
            LayoutInflater.from(parent?.context!!).inflate(R.layout.series_super_hero_templete , parent , false)
        )
    }

    override fun onBindViewHolder(holder: SeriousMovieViewHolder, position: Int) {
        holder?.bindSeriesDetailToView(listOfSeriousDetails?.get(position) , listener)
    }

    override fun getItemCount(): Int {
        return listOfSeriousDetails?.size
    }


    public class SeriousMovieViewHolder ( override val containerView: View?)  :RecyclerView.ViewHolder(containerView!!) ,
        LayoutContainer {

        public fun bindSeriesDetailToView (superHero : PresentationSuperHeroDetail , listener : SeriesMoviesListener){
            Glide.with(containerView!!).asGif().load(R.drawable.spinner_loading).transform(
                RoundedCorners(30)
            ).into(seriousHeroMovieLoadingImage)

            Picasso.get().load(superHero?.poster)
                .transform(RoundedCornersTransformation(30 , 0))
                .into(seriousHeroMovieImage , object : Callback {
                    override fun onSuccess() {
                        seriousHeroMovieLoadingImage?.visibility = View.INVISIBLE
                    }

                    override fun onError(e: Exception?) {
                        seriousHeroMovieLoadingImage?.visibility = View.INVISIBLE
                    }
                })

            seriesRoot?.setOnClickListener {
                listener?.onSeriesClicked(containerView!! , superHero)
            }
        }

    }

}