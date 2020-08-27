package com.code.cpo.ui

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.code.cpo.http.HttpTime
import com.code.cpolib.R
import kotlinx.android.synthetic.main.item_http.view.*

/**
 *  author : balance
 *  date : 2020/8/20 3:32 PM
 *  description :
 */
class HttpItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    fun setData(httpTime: HttpTime) {
        url.text = httpTime.urlStr
        statu.isSelected = httpTime.success
        time.text = httpTime.totalTime.toString() + "ms"
        size.text = httpTime.size.toString() + "kb"
        type.text = httpTime.type
        setOnClickListener{
            val intent = Intent(context, HttpDetailActivity::class.java)
            intent.putExtra(HttpDetailActivity.DATA, httpTime)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }

    init {
        LayoutInflater.from(context).inflate(R.layout.item_http, this)
    }
}