package com.morse.ui.ui.Extensions

import android.view.View
import com.morse.ui.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

@BindingAdapter("loadImageUsingGlide")
fun loadImageUsingGilde ( image : ImageView , imageUrl : Boolean){
    Glide.with(image?.context).asGif().load(R.drawable.spinner_loading).transform(
        RoundedCorners(25)
    ).into(image)
}

@BindingAdapter(value = [ "loadImageUsingPicassoFail" ,"loadImageUsingPicassoSuccess"]  , requireAll = true)
fun loadImageUsingPicasso( image : ImageView , oldImage: View? ,newImage : String ?){

    oldImage?.visibility= View.VISIBLE
    Picasso.get().load(newImage).transform(RoundedCornersTransformation(25 , 0))
        .into(image , object : Callback {
            override fun onSuccess() {
                oldImage?.visibility= View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                oldImage?.visibility= View.INVISIBLE
            }
        })
}


@BindingAdapter("adapterForSuperheros")
fun bindAdapterSuperHerossList(
    view: RecyclerView,
    posters: ArrayList<PresentationSuperHeroItem>?
) {
    posters.let {
        view.adapter.apply {
            if (this != null) {
                if (it != null) {
                    (this as? SuperHerosAdapter)?.sendNewSuperHeros(it)
                }
            }
        }
    }
}

@BindingAdapter("adapterForSuperherosDetails")
fun bindAdapterSuperHerossListDetails(
    view: RecyclerView,
    posters: ArrayList<PresentationSuperHeroDetail>?
) {
    posters.let {
        view.adapter.apply {
            if (this != null) {
                if (it != null) {
                    (this as? SeriousMovieAdapter)?.submitSerious(it)
                }
            }
        }
    }
}

@BindingAdapter("setupDiscreteViewConfigration")
fun addDiscreteViewConfig (discreteScrollView: DiscreteScrollView , isConfigerd : Boolean){

    discreteScrollView.scrollToPosition(1)
    discreteScrollView?.setItemTransformer(

        ScaleTransformer.Builder()
            .setMaxScale(1.05f)
            .setMinScale(0.8f)
            .setPivotX(Pivot.X.CENTER) // CENTER is a default one
            .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
            .build()

    )
    discreteScrollView?.setSlideOnFling(true)
}
