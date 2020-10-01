package com.morse.ui.ui.Extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

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

    discreteScrollView?.addOnItemChangedListener { viewHolder, adapterPosition ->
//                    if (lastIndex != adapterPosition) {
//                        if (adapterPosition == 0) {
//
//                           // nintyDegreeTriangle?.circularRevealedAtCenter(heros?.get(adapterPosition)?.color!!)
//                        } else {
////
////                            nintyDegreeTriangle?.changeColor(
////                                heros?.get(adapterPosition - 1)?.color!!,
////                                heros?.get(adapterPosition)?.color!!
////                            )
//                        }
//                    }
//                    lastIndex = adapterPosition
    }

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


//@BindingAdapter("bindOnItemChanged" )
//fun bindOnItemChanged(view: DiscreteScrollView, adapter: SuperHerosAdapter, pointView: View) {
//    view.addOnItemChangedListener { viewHolder, _ ->
//        viewHolder?.adapterPosition.apply {
//            ( pointView as NintyDegressTriangleView )?.circularRevealedAtCenter(Color.parseColor(adapter.getListOfSuperHeros().get(this!!).color!!))
//        }
//    }
//}