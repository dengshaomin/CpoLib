package com.code.cpo.ui

import android.app.Application
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.cpo.http.HttpTime

/**
 *  author : balance
 *  date : 2020/8/20 3:39 PM
 *  description :
 */
class HttpAdapter(val context: Application, val httpTimes: MutableList<HttpTime>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = HttpItem(context)
        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun getItemCount(): Int {
        return httpTimes?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as HttpItem).setData(httpTimes?.get(position))
    }
}