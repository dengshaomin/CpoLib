package com.code.cpo.utils

import android.util.Log
import com.code.cpo.CpoTool

/**
 *  author : balance
 *  date : 2020/8/24 3:06 PM
 *  description :
 */
class L {
    companion object {
        fun e(str: String) {
            str.let {
                Log.e(CpoTool.Companion.TAG, str)
            }
        }

    }
}