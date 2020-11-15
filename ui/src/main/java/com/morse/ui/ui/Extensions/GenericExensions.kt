package com.morse.ui.ui.Extensions

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewAnimationUtils
import com.morse.ui.R
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter
import com.morse.ui.ui.detail.view.DetailActivity
import com.morse.ui.ui.home.view.HomeActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception
import kotlin.math.hypot

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

@BindingAdapter("setCurrentFabBackground")
fun setFabBackground (fab : FloatingActionButton , color : String){
    fab?.setBackgroundColor(Color.parseColor(color))
    fab?.rippleColor = (Color.parseColor(color))
    fab?.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
}

fun View.circularRevealedAtCenter(colorPrevious: Int , animationLoading : ObservableBoolean) {
    var ourView = this
    val finalRadius = hypot(width.toDouble() , height.toDouble())
    if (
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            isAttachedToWindow
        } else {
            false
        }
    ) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(this,this?.width /2, this?.height / 2, 0f, finalRadius.toFloat()).apply {
                (ourView as RelativeLayout)?.setBackgroundColor(Color.DKGRAY)
                //visibility = View.VISIBLE
                duration = 500
                addListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        animationLoading.set(true)
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationRepeat(animation: Animator?) {

                    }
                })
                start()

            }


        }
    }
}

public fun View.openDetailScreen (context: Context, superHero :PresentationSuperHeroItem, view: View, loadingView: View, activity: HomeActivity) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        var intent = Intent(context, DetailActivity::class.java)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(
                activity,
                view,
                "superHeroImage" // The transition name to be matched in Activity B.
            )
        intent?.putExtra("superHeroData", superHero)
        context.startActivity(intent, options.toBundle())
        //context.startActivity(intent )
    }
}

