package com.code.cpo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.code.cpo.http.HttpTime
import com.code.cpo.utils.JsonFormatUtil
import com.code.cpolib.R
import kotlinx.android.synthetic.main.activity_http_detail.*
import kotlinx.android.synthetic.main.activity_http_detail.view.*

class HttpDetailActivity : AppCompatActivity() {
    companion object {
        val DATA = "data";
    }

    var httpTime: HttpTime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_detail)
        httpTime = intent.getSerializableExtra((DATA)) as HttpTime
        httpTime.let {
            method.text = httpTime?.type
            url.text = httpTime?.urlStr
            headers.text = httpTime?.headersStr
            request_body.text = httpTime?.requestBodyStr
            response.text = httpTime?.responseStr?.let { it1 -> JsonFormatUtil.formatString(it1) }
            time.text = httpTime?.totalTime.toString() + "ms"
            dns.text = "dns: " + httpTime?.dnsTime.toString() + "ms"
            size.text = httpTime?.size.toString() + "Kb"
        }
    }
}