package com.code.cpo.utils

import java.math.BigDecimal

/**
 *  author : balance
 *  date : 2020/8/21 11:02 AM
 *  description :
 */
class TimeUtil {
    companion object{
        fun formatTime(f:Double):Double{
            val bg = BigDecimal(f)
            val f1: Double = bg.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            return f1
        }
    }
}