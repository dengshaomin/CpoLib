package com.code.cpodemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap


/**
 *  author : balance
 *  date : 2020/8/19 9:57 AM
 *  description :
 */
interface IPost {
    @GET("query")
    @Headers("header0:0", "header1:1")
    fun request(@QueryMap map: Map<String, String>): Call<PostBean>
}