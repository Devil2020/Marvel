package com.morse.ui.ui.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultErrorState
import com.morse.presentation.state.ResultState
import com.morse.presentation.viewmodel.SuperHeroViewModel
import com.morse.ui.R
import com.morse.ui.databinding.ActivityHomeBinding
import com.morse.ui.ui.detail.view.DetailActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.ninty_degree_traingle_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel



object HomeActivityManager {
    public fun openDetailScreen (context: Context , superHero :PresentationSuperHeroItem){
        var intent = Intent (context , DetailActivity::class.java)
        intent?.putExtra("superHeroData" , superHero)
        context.startActivity(intent)
    }
}


class HomeActivity : AppCompatActivity()  {
    var heros = arrayListOf<PresentationSuperHeroItem>()
    var superHerosAdapter =  SuperHerosAdapter ()
    val homeViewModel : SuperHeroViewModel by viewModel<SuperHeroViewModel>()
    var lastIndex = -1
    lateinit var homeDataBinding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeDataBinding = DataBindingUtil.setContentView<ActivityHomeBinding>(this ,R.layout.activity_home)
        homeDataBinding.superheroViewModel = homeViewModel
        superHeroview?.apply {
                this?.addOnItemChangedListener { viewHolder, adapterPosition ->
                    if (lastIndex != adapterPosition) {
                        if (adapterPosition == 0) {

                            nintyDegreeTriangle?.changeColor(heros?.get(adapterPosition)?.color!!)
                        } else {

                            nintyDegreeTriangle?.changeColor(
                                heros?.get(adapterPosition - 1)?.color!!,
                                heros?.get(adapterPosition)?.color!!
                            )
                        }
                    }
                    lastIndex = adapterPosition
                }
                this.scrollToPosition(3)
                this?.setItemTransformer(

                    ScaleTransformer.Builder()
                        .setMaxScale(1.05f)
                        .setMinScale(0.8f)
                        .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                        .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                        .build()

                )
                this?.setSlideOnFling(true)
        }
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    public fun register(){
        homeViewModel?.superHerosResult.observe(this ,object : Observer<ResultState<PresentationSuperHeroItem>>{
            override fun onChanged(t: ResultState<PresentationSuperHeroItem>?) {
                if (t is ResultState.success<*>){
                    heros?.clear()
                    heros?.addAll(t?.result as ArrayList<out PresentationSuperHeroItem>)
                    superHeroview?.apply {
                        this?.adapter = superHerosAdapter
                    }
                    superHerosAdapter?.sendNewSuperHeros(t?.result as ArrayList<PresentationSuperHeroItem>)
                    loadingShimmerLayout?.apply{
                        stopShimmer()
                        visibility = View.INVISIBLE
                    }
                    noInternetRoot?.apply {
                        visibility = View.INVISIBLE
                    }
                }
                else if (t is ResultState.isLoading) {
                    loadingShimmerLayout?.apply {
                        visibility = View.VISIBLE
                        startShimmer()
                    }
                    Toast.makeText(this@HomeActivity , "Start Loading" , Toast.LENGTH_SHORT).show()
                    noInternetRoot?.apply {
                        visibility = View.INVISIBLE
                    }
                }
                else if (t is ResultState.error){

                    if (t?.errorType == ResultErrorState.NO_INTERNET){

                        Toast.makeText(this@HomeActivity , "No Internet , Please Try Again" , Toast.LENGTH_SHORT).show()

                        errorText?.text = "No Internet , Please try again later"

                    }
                    else {

                        Toast.makeText(this@HomeActivity , "Time Out , Please Try Again" , Toast.LENGTH_SHORT).show()

                        errorText?.text = " TimeOut , Please try again later"

                    }
                    noInternetRoot?.apply {
                        visibility = View.VISIBLE
                    }

                    loadingShimmerLayout?.apply{
                        stopShimmer()
                        visibility = View.INVISIBLE
                    }
                }

            }

        })
    }


}