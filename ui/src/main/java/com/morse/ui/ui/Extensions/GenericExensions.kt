package com.morse.ui.ui.Extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroDetail
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.ui.ui.detail.adapter.SeriousMovieAdapter

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


//@BindingAdapter("bindOnItemChanged" )
//fun bindOnItemChanged(view: DiscreteScrollView, adapter: SuperHerosAdapter, pointView: View) {
//    view.addOnItemChangedListener { viewHolder, _ ->
//        viewHolder?.adapterPosition.apply {
//            ( pointView as NintyDegressTriangleView )?.circularRevealedAtCenter(Color.parseColor(adapter.getListOfSuperHeros().get(this!!).color!!))
//        }
//    }
//}