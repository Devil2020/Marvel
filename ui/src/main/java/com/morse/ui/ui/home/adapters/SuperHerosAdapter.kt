package com.morse.marvel.ui.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import com.morse.ui.R
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.super_hero_templete.*
import java.lang.Exception

class SuperHerosAdapter () : RecyclerView.Adapter<SuperHerosAdapter.SuperHeroViewHolder> (){

    private var listOfSuperHeros = arrayListOf<PresentationSuperHeroItem>()

    public fun sendNewSuperHeros (newData : ArrayList<PresentationSuperHeroItem>){
        listOfSuperHeros?.clear()
        listOfSuperHeros?.addAll(newData)
        this?.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        var view = LayoutInflater.from(parent?.context!!).inflate(R.layout.super_hero_templete , parent , false)
        return SuperHeroViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder?.bindSuperHeroToView(listOfSuperHeros?.get(position))
    }

    override fun getItemCount(): Int {
        return listOfSuperHeros?.size
    }

     public class SuperHeroViewHolder ( override val containerView: View?)  :RecyclerView.ViewHolder(containerView!!) , LayoutContainer{

        public fun bindSuperHeroToView (superHero : PresentationSuperHeroItem){
                Glide.with(containerView!!).asGif().load(R.drawable.spinner_loading).transform(RoundedCorners(40)).into(loading)

                Picasso.get().load(superHero?.poster)
                    .transform(RoundedCornersTransformation(40 , 0))
                    .into(superHeroLoadingImage , object : Callback{
                        override fun onSuccess() {
                            loading?.visibility = View.INVISIBLE
                        }

                        override fun onError(e: Exception?) {
                            loading?.visibility = View.INVISIBLE
                        }
                    })
                heroName?.setText(superHero?.name)
        }

    }

}