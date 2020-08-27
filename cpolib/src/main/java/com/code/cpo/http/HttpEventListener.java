package com.code.cpo.http;

import com.code.cpo.CpoTool;
import com.code.cpo.utils.L;
import com.code.cpo.utils.TimeUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : balance
 * date : 2020/8/19 2:15 PM
 * description :
 */
public class HttpEventListener extends EventListener {
    /**
     * 每次请求的标识
     */
    private long callId;
    private HttpUrl url;
    /**
     * 每次请求的开始时间，单位ms
     */
    private Long callStartNanos;

    private HttpTime httpTime = new HttpTime();
    private StringBuilder sbLog;
    private static final long MS = 1000000;
    private static final String injectResponseStr = "injectResponseStr";

    /**
     * 自定义EventListener工厂
     */
    public static Factory FACTORY = new Factory() {
        @Override
        public EventListener create(Call call) {
            long callId = nextCallId.getAndIncrement();
            return new HttpEventListener(callId, call.request().url(), System.nanoTime());
        }
    };
    public static AtomicLong nextCallId = new AtomicLong(1L);

    HttpEventListener(long callId, HttpUrl url, long time) {
        this.callId = callId;
        this.url = url;
        this.callStartNanos = time;
        sbLog = new StringBuilder(url.toString()).append(" ").append(callId).append(":");
    }

    private void recordEventLog(Status statu) {
        long current = System.nanoTime();
        long elapseNanos = current - callStartNanos;
        sbLog.append(statu.toString()).append("-").append(elapseNanos).append(";");
        switch (statu) {
            //start
            case CALL_START:
                httpTime.setCallStart(current);
                //dns
            case DNS_START:
                httpTime.setDnsStart(current);
            case DNS_END:
                httpTime.setDnsEnd(current);
                //connect start
            case CONNECT_START:
                httpTime.setConnectStart(current);
            case SECURE_CONNECT_START:
                httpTime.setSecureConnectStart(current);
            case SECURE_CONNECT_END:
                httpTime.setSecureConnectEnd(current);
                //connect end
            case CONNECT_END:
                httpTime.setConnectEnd(current);
            case CONNECT_FAIL:
                httpTime.setConnectFail(current);
            case CONNECTION_ACQUIRED:
                httpTime.setConnectionAcquired(current);
            case CONNECTION_RELEASED:
                httpTime.setConnectionReleased(current);
                //request
            case REQUEST_HEAD_START:
                httpTime.setRequestHeadersStart(current);
            case REQUEST_HEAD_END:
                httpTime.setRequestHeadersEnd(current);
            case REQUEST_BODY_START:
                httpTime.setRequestBodyStart(current);
            case REQUEST_BODY_END:
                httpTime.setRequestBodyEnd(current);
                //reponse
            case RESPONSE_HEADER_START:
                httpTime.setResponseHeadersStart(current);
            case RESPONSE_HEADER_END:
                httpTime.setResponseBodyEnd(current);
            case RESPONSE_BODY_START:
                httpTime.setResponseBodyStart(current);
            case RESPONSE_BODY_END:
                httpTime.setResponseBodyEnd(current);
                //end
            case CALL_END:
                httpTime.setCallEnd(current);
            case CALL_FAIL:
                httpTime.setCallFail(current);
        }
        if (statu == Status.CALL_END || statu == Status.CALL_FAIL) {
            //打印出每个步骤的时间点
            if (statu == Status.CALL_FAIL) {
                httpTime.setSuccess(false);
                httpTime.setTotalTime((httpTime.getCallFail() - httpTime.getCallStart()) / MS);
            } else {
                httpTime.setTotalTime((httpTime.getCallEnd() - httpTime.getCallStart()) / MS);
            }
            httpTime.setDnsTime((httpTime.getDnsEnd() - httpTime.getDnsStart()) / MS);
            httpTime.setCallId(callId);
            httpTime.setUrlStr(url.toString());

            L.Companion.e(sbLog.toString());
            CpoTool.Companion.updateHttp(httpTime);
        }
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        httpTime.setType(call.request().method());
        if (call.request().headers() != null) {
            httpTime.setHeadersStr(call.request().headers().toString());
        }
        if (call.request().body() != null) {
            httpTime.setRequestBodyStr(call.request().body().toString());
        }
        recordEventLog(Status.CALL_START);
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        recordEventLog(Status.DNS_START);
    }

    @Override
    public void

    dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        recordEventLog(Status.DNS_END);
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        recordEventLog(Status.CONNECT_START);
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
        recordEventLog(Status.SECURE_CONNECT_START);
    }

    @Override
    public void secureConnectEnd(Call call, Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        recordEventLog(Status.SECURE_CONNECT_END);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        recordEventLog(Status.CONNECT_END);
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        recordEventLog(Status.CONNECT_FAIL);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
        recordEventLog(Status.CONNECTION_ACQUIRED);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
        recordEventLog(Status.CONNECTION_RELEASED);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
        recordEventLog(Status.REQUEST_HEAD_START);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
        recordEventLog(Status.REQUEST_HEAD_END);
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
        recordEventLog(Status.REQUEST_BODY_START);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        recordEventLog(Status.REQUEST_BODY_END);
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
        recordEventLog(Status.RESPONSE_HEADER_START);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
        recordEventLog(Status.RESPONSE_HEADER_END);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
        recordEventLog(Status.RESPONSE_BODY_START);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        httpTime.setSize((float) TimeUtil.Companion.formatTime(byteCount / 1024f));
        recordEventLog(Status.RESPONSE_BODY_END);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        refectResponse(call);
        recordEventLog(Status.CALL_END);
    }

    private void refectResponse(Call call) {
        if (call == null) {
            return;
        }
        try {
            httpTime.setResponseStr(call.getClass().getField(injectResponseStr).get(call).toString());
        } catch (Exception e) {
            e.printStackTrace();
            L.Companion.e(e.getMessage());
        }
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        recordEventLog(Status.CALL_FAIL);
    }

}