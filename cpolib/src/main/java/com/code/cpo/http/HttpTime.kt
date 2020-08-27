package com.code.cpo.http

import java.io.Serializable

/**
 *  author : balance
 *  date : 2020/8/20 4:04 PM
 *  description :
 */
class HttpTime() : Serializable {
    var type: String? = "GET"
    var urlStr: String? = null
    var headersStr: String? = null
    var requestBodyStr: String? = null
    var responseStr: String? = null
    var size: Float = 0f
    var totalTime = 0L
    var dnsTime = 0L
    var callId = 0L
    var success = true
    var callStart = 0L
    var dnsStart = 0L
    var dnsEnd = 0L
    var secureConnectStart = 0L
    var secureConnectEnd = 0L
    var connectStart = 0L;
    var connectEnd = 0L
    var connectFail = 0L
    var connectionAcquired = 0L
    var requestHeadersStart = 0L
    var requestHeadersEnd = 0L
    var requestBodyStart = 0L
    var requestBodyEnd = 0L
    var responseHeadersStart = 0L;
    var responseHeadersEnd = 0L
    var responseBodyStart = 0L
    var responseBodyEnd = 0L
    var connectionReleased = 0L
    var callEnd = 0L;
    var callFail = 0L


}