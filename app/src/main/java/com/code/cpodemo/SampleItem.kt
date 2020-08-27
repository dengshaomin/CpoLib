package com.code.cpodemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.sample_item.view.*

/**
 *  author : balance
 *  date : 2020/8/17 1:55 PM
 *  description :
 */
class SampleItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    val data: IntArray = intArrayOf()

    init {
        LayoutInflater.from(context).inflate(R.layout.sample_item, this)

    }

    fun onBind(value: Int, megaBytes: Float) {
        configureColor(value)
        val total = (megaBytes * 100f).toInt()
        val start = SystemClock.elapsedRealtime()
        val startInt = start.toInt()
        for (i in 0 until total) {
            for (e in data.indices) {
                // set dummy value (start time)
                data[e] = startInt
            }
        }
        val end = SystemClock.elapsedRealtime()
        val bindTimeMs = end - start
        bindTime.setText(bindTimeMs.toString() + "ms onBind()")
    }

    private fun configureColor(value: Int) {
        val multiplier = 22
        val hundred = value / 100
        val tens = (value - hundred * 100) / 10
        val ones = value - hundred * 100 - tens * 10
        val r = hundred * multiplier
        val g = tens * multiplier
        val b = ones * multiplier
        val colorVal = Color.rgb(r, g, b)
        colorImg.setImageDrawable(ColorDrawable(colorVal))
    }
}