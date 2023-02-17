package com.tg.vloan.net;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by frcx-hb on 2022/12/3 16:52.
 * 日志拦截器
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "LogInterceptor";
    private static final String MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private LogLevel logLevel = LogLevel.BODY;
    private ColorLevel colorLevel = ColorLevel.VERBOSE;
    private String logTag = TAG;

    private String toDateTimeStr(Long millis, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(millis);
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setColorLevel(ColorLevel colorLevel) {
        this.colorLevel = colorLevel;
    }

    public void setLogTag(String logTag) {
        this.logTag = logTag;
    }


    public enum LogLevel {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public enum ColorLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    private void logResponse(Response response) {
        StringBuffer sb = new StringBuffer();
        sb.append("  ");
        sb.append("\n<<<<<<<<<<<<<<<<<<<<<<<<Response<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        sb.append("\n");
        switch (logLevel) {
            case NONE:
                break;
            case BASIC:
                logBasicRsp(sb, response);
                break;
            case HEADERS:
                logHeadersRsp(sb, response);
                break;
            case BODY:
                logHeadersRsp(sb, response);
                ResponseBody peekBody;
                try {
                    peekBody = response.peekBody(1024 * 1024);
                    sb.append("response body:\n" + new String(peekBody.bytes(), Charset.defaultCharset()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        sb.append("\n--------------------------------------------------------------------");
        sb.append("\r\n\r");
        logIt(sb.toString());
    }

    private void logRequest(Request request, Connection connection) {
        StringBuffer sb = new StringBuffer();
        sb.append("  ");
        sb.append("\n>>>>>>>>>>>>>>>>>>>>>>>>>>request>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        sb.append("\n");
        switch (logLevel) {
            case BODY:
                logBodyReq(sb, request, connection);
                sb.append("\n");
                break;
            case NONE:
                break;
            case BASIC:
                logBasicReq(sb, request, connection);
                break;
            case HEADERS:
                logHeaderReq(sb, request, connection);
                break;
        }
        sb.append("--------------------------------------------------------------------");
        logIt(sb.toString());
    }


    private void logBasicRsp(StringBuffer sb, Response response) {
        String s = toDateTimeStr(response.sentRequestAtMillis(), MILLIS_PATTERN);
        sb.append("response protocol: ");
        sb.append(response.protocol());
        sb.append("\n");
        sb.append("response code: ").append(response.code());
        sb.append("\n");
        sb.append("response message: ").append(response.message());
        sb.append("\n");
        sb.append("response request Url: ").append(decodeUrlString(response.request().url().toString()));
        sb.append("\n");
        sb.append("response sentRequestTime:").append(s);
        sb.append("\n");

    }

    private void logHeadersRsp(StringBuffer sb, Response response) {
        logBasicRsp(sb, response);
        Headers headers = response.headers();
        for (int i = 0; i < headers.size(); i++) {
            sb.append("response Header:").append(headers.name(i)).append(" = ").append(headers.value(i));
            sb.append("\n");
        }
    }


    private String decodeUrlString(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void logIt(String sb) {
        switch (colorLevel) {
            case VERBOSE:
                Log.v(logTag, sb);
                Log.v("network", sb);
                break;
            case INFO:
                Log.i(logTag, sb);
                Log.i("network", sb);
                break;
            case DEBUG:
                Log.d(logTag, sb);
                Log.d("network", sb);
                break;
            case ERROR:
                Log.e(logTag, sb);
                Log.e("network", sb);
                break;
            case WARN:
                Log.w(logTag, sb);
                Log.w("network", sb);
                break;
        }
    }


    private void logBodyReq(StringBuffer sb, Request request, Connection connection) {
        logHeaderReq(sb, request, connection);
        sb.append("RequestBody: ").append(Objects.requireNonNull(bodyToString(request)));
    }

    private void logHeaderReq(StringBuffer sb, Request request, Connection connection) {
        logBasicReq(sb, request, connection);
        Headers headers = request.headers();
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            String headersStr = "request Header: " + name + "=" + value + "\n";
            sb.append(headersStr);
        }
    }

    private String bodyToString(Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            RequestBody body = copy.body();
            if (body != null) {
                body.writeTo(buffer);
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "error";
        }
    }

    private void logBasicReq(StringBuffer sb, Request request, Connection connection) {
        sb.append("method: ");
        sb.append(request.method());
        sb.append("\n");
        sb.append("url: ");
        sb.append(decodeUrlString(request.url().toString()));
        sb.append("\n");
        sb.append("tag: ");
        sb.append(request.tag());
        sb.append("\n");
        sb.append("protocol:  ");
        if (connection != null) {
            sb.append(connection.protocol());
        } else {
            sb.append(okhttp3.Protocol.HTTP_1_1);
        }
        sb.append("\n");
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        if (proceed.isSuccessful()) {
            if (logLevel == LogLevel.NONE) {
                return proceed;
            }
            Connection connection = chain.connection();
            logRequest(request, connection);
            logResponse(proceed);
        } else {
            String message = proceed.message();
            logIt(message);
        }
        return proceed;
    }
}
