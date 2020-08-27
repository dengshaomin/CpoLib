package com.code.cpodemo.convert;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * author : balance
 * date : 2020/8/19 11:14 AM
 * description :
 */
public class FastJsonRequestConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public RequestBody convert(T value) {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
