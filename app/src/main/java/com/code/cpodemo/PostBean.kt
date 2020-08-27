package com.code.cpodemo

/**
 *  author : balance
 *  date : 2020/8/19 9:58 AM
 *  description :
 */
class PostBean {
    var message: String? = null
    var nu: String? = null
    var ischeck: String? = null
    var com: String? = null
    var status: String? = null
    var condition: String? = null
    var state: String? = null
    var data: List<DataBean?>? = null


    class DataBean {
        /**
         * time : 2020-08-06 09:57:06
         * context : 查无结果
         * ftime : 2020-08-06 09:57:06
         */
        var time: String? = null
        var context: String? = null
        var ftime: String? = null

    }
}