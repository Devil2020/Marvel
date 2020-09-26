package com.morse.ui.ui.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding2.view.RxView
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultErrorState
import com.morse.presentation.state.ResultState
import com.morse.presentation.viewmodel.SuperHeroViewModel
import com.morse.ui.R
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.ninty_degree_traingle_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity() {
    var heros = arrayListOf<PresentationSuperHeroItem>()
    var superHerosAdapter =  SuperHerosAdapter ()
    val homeViewModel : SuperHeroViewModel by viewModel<SuperHeroViewModel>()
    lateinit var compositeDisposable: CompositeDisposable
    var lastIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_home)
        compositeDisposable = CompositeDisposable()
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
        loadSuperHeros()
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    public fun loadSuperHeros (){
        homeViewModel?.loadSuperHeros()?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
            if (it is ResultState.success<*>){
                heros?.clear()
                heros?.addAll(it?.result as ArrayList<out PresentationSuperHeroItem>)
                superHeroview?.apply {
                    this?.adapter = superHerosAdapter
                }
                superHerosAdapter?.sendNewSuperHeros(it?.result as ArrayList<PresentationSuperHeroItem>)
                loadingShimmerLayout?.apply{
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
                noInternetRoot?.apply {
                    visibility = View.INVISIBLE
                }
            }
            else if (it is ResultState.isLoading) {
                loadingShimmerLayout?.startShimmer()
                Toast.makeText(this , "Start Loading" , Toast.LENGTH_SHORT).show()
                noInternetRoot?.apply {
                    visibility = View.INVISIBLE
                }
            }
            else if (it is ResultState.error){
                if (it?.errorType == ResultErrorState.NO_INTERNET){
                    Toast.makeText(this , "No Internet , Please Try Again" , Toast.LENGTH_SHORT).show()
                    errorText?.text = "No Internet , Please try again later"
                }
                else {
                    Toast.makeText(this , "Time Out , Please Try Again" , Toast.LENGTH_SHORT).show()
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

        }?.addTo(compositeDisposable)
    }

    public fun register(){
        RxView.clicks(tryAgainButton)?.debounce(1 , TimeUnit.SECONDS)?.subscribe {
            loadSuperHeros()
        }?.addTo(compositeDisposable)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable?.dispose()
    }
}