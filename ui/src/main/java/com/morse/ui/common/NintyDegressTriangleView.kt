package com.morse.marvel.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.Region
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.DrawableCompat
import com.morse.ui.R
import kotlinx.android.synthetic.main.rectangle_layout.view.*
import kotlin.math.hypot


class NintyDegressTriangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    public lateinit var view : View

    init {
        view = LayoutInflater.from(context).inflate(R.layout.rectangle_layout, this, true)

        //changeColor(view?.resources?.getColor(R.color.colorAccent)!!)
    }

    public fun changeColor(color: Int){
        view?.triangleRoot?.setBackgroundColor(color)
    }

    public fun changeColor(colorPrevious: String){

        view?.triangleRoot?.setBackground(ColorDrawable(Color.parseColor(colorPrevious)))

    }


    fun circularRevealedAtCenter(colorPrevious: Int) {

        val finalRadius = hypot(width.toDouble() /2, height.toDouble()/2)
        if (
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                isAttachedToWindow
            } else {
                false
            }
        ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ViewAnimationUtils.createCircularReveal(view?.triangleRoot, view?.triangleRoot?.right!!, view?.triangleRoot?.bottom!!, 0f, finalRadius.toFloat()).apply {
                    view?.triangleRoot?.setBackgroundColor(colorPrevious)
                    visibility
                    duration = 800
                    start()
                }
            }
        }
    }

    public fun changeColor(colorPrevious: String , colorCurrent : String){
        val colorDrawables = arrayOf(
            ColorDrawable(Color.parseColor(colorPrevious)),
            ColorDrawable(Color.parseColor(colorCurrent))
        )
        val transitionDrawable = TransitionDrawable(colorDrawables)
        view?.triangleRoot?.setBackground(transitionDrawable)
        transitionDrawable.startTransition(200)

    }

    private val path = Path()

    override fun dispatchDraw(canvas: Canvas?) {
        val count = canvas!!.save()
        //RevesedArcView path
        path.moveTo(0f, canvas.height.toFloat())
        path.lineTo(canvas.width.toFloat(), canvas.height.toFloat())
        path.lineTo(canvas.width.toFloat(), ((canvas.height.toFloat() / 3)))
        path.lineTo(0f, canvas.height.toFloat())
        canvas.clipPath(path, Region.Op.INTERSECT)
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(count)
    }



}