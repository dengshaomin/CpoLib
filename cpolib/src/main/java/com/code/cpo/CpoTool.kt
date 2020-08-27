package com.code.cpo

import android.content.Context
import com.code.cpo.fps.TinyDancerBuilder
import com.code.cpo.http.HttpTime
import com.code.cpo.utils.ScreenUtils

/**
 *  author : balance
 *  date : 2020/8/20 1:45 PM
 *  description :
 */
class CpoTool {
    companion object {
        var a = 1
        val TAG = CpoTool::class.java.simpleName
        var tinyDancerBuilder: TinyDancerBuilder? = null
        fun init(context: Context) {
            if(tinyDancerBuilder == null) {
                var width = ScreenUtils.getScreenWidth(context)
                tinyDancerBuilder = TinyDancerBuilder()
                tinyDancerBuilder?.redFlagPercentage(.1f)
                    ?.startingXPosition(width)
                    ?.startingYPosition(0)
                    ?.show(context);
            }
        }

        fun updateHttp(httpTime: HttpTime) {
            tinyDancerBuilder?.updateHttp(httpTime)
        }

        fun show(applicationContext: Context?) {
            tinyDancerBuilder?.show(applicationContext)
        }
        fun hide(applicationContext: Context?){
            tinyDancerBuilder?.hide(applicationContext)
        }
    }
}