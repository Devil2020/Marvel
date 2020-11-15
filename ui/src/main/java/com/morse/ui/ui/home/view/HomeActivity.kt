package com.morse.ui.ui.home.view

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.animation.BounceInterpolator
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.morse.marvel.common.NintyDegressTriangleView
import com.morse.marvel.ui.ui.home.adapters.SuperHerosAdapter
import com.morse.presentation.presentationentity.PresentationSuperHeroItem
import com.morse.presentation.state.ResultErrorState
import com.morse.presentation.state.ResultState
import com.morse.presentation.viewmodel.SuperHeroViewModel
import com.morse.ui.R
import com.morse.ui.databinding.ActivityHomeBinding
import com.morse.ui.ui.Extensions.circularRevealedAtCenter
import com.morse.ui.ui.detail.view.DetailActivity
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.ninty_degree_traingle_layout.*
import kotlinx.android.synthetic.main.rectangle_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.math.hypot

class HomeActivity : AppCompatActivity()  {
    var superHerosAdapter =  SuperHerosAdapter (this)
    val homeViewModel : SuperHeroViewModel by viewModel<SuperHeroViewModel>()
    lateinit var homeDataBinding : ActivityHomeBinding
    lateinit var compositeDisposable : CompositeDisposable
    var animationLoading = ObservableBoolean()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        compositeDisposable = CompositeDisposable()
        setubDataBinding()
        animateScreen()
    }
    private fun setubDataBinding (){
        homeDataBinding = DataBindingUtil.setContentView<ActivityHomeBinding>(this ,R.layout.activity_home)
        homeDataBinding.superheroViewModel = homeViewModel
        homeDataBinding.afterLoadingFinish = animationLoading
        superHeroview?.apply {
            this?.adapter = superHerosAdapter
            this?.addOnItemChangedListener { viewHolder, adapterPosition ->
                nintyDegreeTriangle?.circularRevealedAtCenter(Color.parseColor(superHerosAdapter?.getListOfSuperHeros().get(adapterPosition).color!!))
            }
        }
        //executeMe()
    }
    private fun executeMe (){
        var integerTransform = ObservableTransformer<Int , Int>{
            it?.map {
                it * 2
            }
        }

        var stringTransform = ObservableTransformer<String , String>{
            it?.map {
               it + "Edit"
            }
        }

        var ourObservable = Observable.just(1,2,3,"Ahmed" , "Mona")
        var result = ourObservable?.ofType(String::class.java)?.compose(stringTransform)
        result?.subscribe({
            Toast.makeText(this , it?.toString() , Toast.LENGTH_SHORT).show()
        })
    }
    private fun animateScreen () {
        animationLoading.set(false)
        Observable.timer(0 , TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())?.subscribe {
            homeDataBinding?.inkRippleView?.circularRevealedAtCenter(R.color.colorwhite , animationLoading)
        }?.addTo(compositeDisposable)

    }
    override fun onStop() {
        super.onStop()
        compositeDisposable?.dispose()
    }
}