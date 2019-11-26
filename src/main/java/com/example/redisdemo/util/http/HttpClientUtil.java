package com.example.redisdemo.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.redisdemo.util.GZIPRequestInterceptor;
import com.example.redisdemo.util.GZIPResponseInterceptor;
import com.example.redisdemo.util.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wc
 */
public class HttpClientUtil {

    protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager connectionManager;

    public static CloseableHttpClient httpclient = null;

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 250;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 3 * 1000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 8 * 1000;

    public final static int BUFFER_SIZE = 4096;

    /**
     * 数据传输最长时间
     */
    public final static int SO_TIMEOUT = 60 * 1000;

    //默认重新尝试次数
    public final static int DEFAULT_RETRY_TIME = 3;

    //字符编码
    public static final String CHARSET = "UTF-8";

    /**
     * 从连接池获取连接的超时时间
     */
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 1000;

    /**
     * 客户端和服务器建立连接的超时时间
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 3 * 1000;

    /**
     * 客户端从服务器读取数据的超时时间
     */
    private static final int DEFAULT_SOCKET_TIMEOUT = 3 * 1000;

    private static SSLConnectionSocketFactory sslsf;

    private static SSLContext sslcontext;
    private static HttpRequestRetryHandler requestRetryHandler;

    static {
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(true)
                .setSoTimeout(SO_TIMEOUT)
                .setTcpNoDelay(false)
                .setSoReuseAddress(true)
                .setSoKeepAlive(true)
                .build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setBufferSize(BUFFER_SIZE)
                .build();
        requestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int retryCount, HttpContext httpContext) {
                return retryCount < DEFAULT_RETRY_TIME && (exception instanceof NoHttpResponseException || exception
                        instanceof InterruptedIOException || exception instanceof UnknownHostException || exception
                        instanceof ConnectException || exception instanceof SSLException);
            }
        };
        try {
            sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build());
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        httpclient = HttpClients.custom()
                .addInterceptorFirst(new GZIPRequestInterceptor())
                .addInterceptorFirst(new GZIPResponseInterceptor())
                .setConnectionManager(connectionManager)
                .setDefaultConnectionConfig(connectionConfig)
                .setRetryHandler(requestRetryHandler)
//				.setRedirectStrategy(r)
                .build();
    }

    public static void printHeader(HttpResponse response) {
        Header headers[] = response.getAllHeaders();
        StringBuilder cookie = new StringBuilder();
        int ii = 0;
        while (ii < headers.length) {
            logger.info("{}:{}", headers[ii].getName(), headers[ii].getValue());
            if (StringUtils.equalsIgnoreCase(headers[ii].getName(), "Set-Cookie")) {
                cookie.append(headers[ii].getValue()).append(";");
            }
            ++ii;
        }
        logger.info(cookie.toString());
    }

    public static String doPost(String url, JSONObject jsonObject, int retryTime) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doPost(url, map, retryTime, false);
    }


    public static String doPost(String url, JSONObject jsonObject) throws Exception {
        return doPost(url, jsonObject, DEFAULT_RETRY_TIME);
    }


    /**
     * @param url  地址
     * @param json json内容串
     * @return java.lang.String
     * @desc post请求, json串格式
     * @author xiaoping.zhang
     * @date 2018/12/11 17:42
     */
    public static String doPost(String url, String json) throws IOException {
        String result = null;
        HttpPost post = null;
        CloseableHttpResponse res = null;
        try {
            logger.info("url:{}, 参数:{}", url, json);
            post = new HttpPost(url);
            StringEntity s = new StringEntity(json, ContentType.APPLICATION_JSON);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);// 设置请求体
            post.setConfig(buildRequestConfig(true));
            res = httpclient.execute(post);
            result = EntityUtils.toString(res.getEntity());// 返回json格式
        } catch (Exception e) {
            logger.error("网络异常:" + e.getMessage());
            throw e;
        } finally {
            if (post != null) {
                post.abort();
            }
            if (res != null) {
                res.close();
            }
        }

        return result;
    }

    /**
     * 构建请求配置信息
     *
     * @param checkEnabled
     * @return
     */
    private static RequestConfig buildRequestConfig(boolean checkEnabled) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setStaleConnectionCheckEnabled(checkEnabled)
                .build();
        return requestConfig;
    }


    /**
     * @param url  地址
     * @param url  请求头
     * @param json json内容串
     * @return java.lang.String
     * @desc post请求, json串格式，带请求头
     * @author xiaoping.zhang
     * @date 2019/3/7 14:13
     */
    public static String doPost(String url, Map<String, String> head, String json) throws IOException {
        String result = null;
        HttpPost post = null;
        CloseableHttpResponse res = null;
        try {
            logger.info("url:{},请求头:{},参数:{}", url, JsonUtils.obj2Json(head), json);
            post = new HttpPost(url);
            StringEntity s = new StringEntity(json, ContentType.APPLICATION_JSON);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);// 设置请求体
            post.setConfig(buildRequestConfig(true));
            if (null != head && head.size() > 0) {
                for (Map.Entry<String, String> entry : head.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            res = httpclient.execute(post);
            result = EntityUtils.toString(res.getEntity());// 返回json格式
            logger.info("状态码[{}],返回:[{}]", HttpStatus.SC_OK, result);
        } catch (Exception e) {
            logger.error("网络异常" + e.getMessage());
            throw e;
        } finally {
            if (post != null) {
                post.abort();
            }
            if (res != null) {
                res.close();
            }
        }

        return result;
    }


    public static void main(String args[]) throws Exception {
        String giftUrl = "http://13.14.100.14:18181/centerDocker/api/GiftWebApiServiceImpl/findGiftByParams?sign=f969e487e51c1a82aca061cd43da32d3";
        String content = "{\"regionNames\":[\"全国\"],\"flag\":1,\"activityTime\":[1544692620000,1545383820000],\"productCodes\":[\"1011433\"],\"activityType\":3}";
//        String result = HttpClientUtil.doPost(giftUrl, content);
//        System.out.println("result:"+result);

        Map<String, String> head = Maps.newHashMap();
        head.put("Authorization", "Bearer ");
        String result = HttpClientUtil.doPost(giftUrl, head, content);
        System.out.println("result:" + result);
    }

    public static String doPost(String url, Map<String, Object> map, int retryTime, boolean checkEnabled) throws Exception {
        try {
            return doPost(url, map, checkEnabled);
        } catch (Exception e) {
            boolean canRetry = retryTime > 1 && (e instanceof NoHttpResponseException || e instanceof UnknownHostException || e instanceof HttpException);
            if (canRetry) {
                retryTime--;
                return doPost(url, map, retryTime, checkEnabled);
            } else {
                throw e;
            }
        }
    }

    public static String sendGet(String url, String akId, String akSecret) throws Exception {
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            URL realUrl = new URL(url);
            String method = "GET";
            String accept = "json";
            String contentType = "application/json";
            String path = realUrl.getFile();

            SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
            df.setTimeZone(new SimpleTimeZone(0, "GMT"));
            String date = df.format(new Date());

            StringBuilder sb = new StringBuilder()
                    .append(method).append("\n").append(accept).append("\n").append("\n")
                    .append(contentType).append("\n").append(date).append("\n").append(path);
            String stringToSign = sb.toString();

            // 2.计算 HMAC-SHA1
            SecretKeySpec signingKey = new SecretKeySpec(akSecret.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(stringToSign.getBytes());
            String signature = (new BASE64Encoder()).encode(rawHmac);

            // 3.得到 authorization header
            String authHeader = "Dataplus " + akId + ":" + signature;

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(CONNECT_TIMEOUT)
                    .build();
            httpGet.setConfig(requestConfig);

            httpGet.addHeader("accept", "json");
            httpGet.addHeader("content-type", "application/json");
            httpGet.addHeader("date", date);
            httpGet.addHeader("Authorization", authHeader);

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            logger.error("send get error", e);
            throw e;
        } finally {
            close(context, httpGet, response);
        }
    }

    public static String doPost(String url, String content, String sign) throws Exception {
        log.info("API，POST过去的数据是：");
        log.info(content);
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(content, CHARSET);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.setEntity(postEntity);
        httpPost.setHeader("sign", sign);

        log.debug("执行请求 " + httpPost.getRequestLine());

        String result = null;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, CHARSET);
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw new Exception(e);
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw new Exception(e);
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw new Exception(e);
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw new Exception(e);
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    public static String doPost(String url, String content, Map<String, String> header) throws Exception {

        log.info("API，POST过去的数据：{}", content);

        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(content, ContentType.APPLICATION_FORM_URLENCODED);

        if (MapUtils.isNotEmpty(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setEntity(postEntity);

        String result = null;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, CHARSET);
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw new Exception(e);
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw new Exception(e);
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw new Exception(e);
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw new Exception(e);
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, String> param, Map<String, String> header) throws Exception {

        HttpPost httpPost = new HttpPost(url);

        UrlEncodedFormEntity formEntity = null;

        if (MapUtils.isNotEmpty(param)) {
            List<NameValuePair> params = Lists.newArrayList();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
            formEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        }

        if (MapUtils.isNotEmpty(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setEntity(formEntity);

        String result;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, CHARSET);
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw new Exception(e);
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw new Exception(e);
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw new Exception(e);
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw new Exception(e);
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, Object> map, boolean checkEnabled) throws Exception {
        logger.info("HttpClientUtil doPost  url:{},param:{}", url, JSON.toJSONString(map));
        HttpClientContext context = HttpClientContext.create();
        HttpEntity entity;
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setStaleConnectionCheckEnabled(checkEnabled)
                .build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            if (MapUtils.isNotEmpty(map)) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }
            response = httpclient.execute(httpPost, context);
            entity = response.getEntity();
            context.getCookieStore().clear();
            String responseStr = EntityUtils.toString(entity, "utf-8");
            logger.info("url:[{}], 返回:[{}]", url, responseStr);
            return responseStr;
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw e;
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw e;
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {

            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
    }


    /**
     * @return java.lang.String
     * @desc 方法描述
     * @author xiaoping.zhang
     * @date 2018/12/11 16:50
     */
    public static String doPost(String url, Map<String, Object> map, boolean checkEnabled, String contentType) throws Exception {
        logger.info("HttpClientUtil doPost  url:{},param:{}", url, JSON.toJSONString(map));
        HttpClientContext context = HttpClientContext.create();
        HttpEntity entity;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", contentType);
        httpPost.setConfig(buildRequestConfig(true));
        CloseableHttpResponse response = null;
        try {
            if (MapUtils.isNotEmpty(map)) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }
            response = httpclient.execute(httpPost, context);
            entity = response.getEntity();
            context.getCookieStore().clear();
            String responseStr = EntityUtils.toString(entity, "utf-8");
            logger.info("url:[{}], 返回:[{}]", url, responseStr);
            return responseStr;
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw e;
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw e;
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
    }

    public static String doPost(String url, String content, boolean checkEnabled) throws Exception {
        log.info("API:{}，POST过去的数据是：{}", url, content);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");
        if (content != null) {
            StringEntity postEntity = new StringEntity(content, CHARSET);
            httpPost.setEntity(postEntity);
        }
        httpPost.setConfig(buildRequestConfig(true));

        log.info("执行请求 " + httpPost.getRequestLine());

        String result;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, CHARSET);
            log.info("返回数据:{}", result);
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw e;
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw e;
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    public static String doPost(String url, String content, int retryTimes, boolean checkEnabled) throws Exception {
        log.info("[HTTP请求] POST请求url:{}, content:{},retryTimes:{}", url, content, retryTimes);
        try {
            return doPostByLoop(url, content, retryTimes, checkEnabled);
        } catch (Exception e) {
            throw new HttpException(String.format(
                    "获取返回值失败, URL: \'%s\' . 尝试 \'%s\' 次",
                    url, Math.max(retryTimes, 1)));
        }
    }

    private static String doPostByLoop(String url, String content, int retryTimes, boolean checkEnabled) throws Exception {
        try {
            return doPost(url, content, checkEnabled);
        } catch (Exception e) {
            boolean canRetry = retryTimes > 1 && (e instanceof NoHttpResponseException || e instanceof UnknownHostException || e instanceof HttpException);
            log.error("[HTTP请求失败] 重试标志:{},请求URL:{}, 异常:{}", canRetry, url, e);
            if (canRetry) {
                retryTimes--;
                return doPost(url, content, retryTimes, checkEnabled);
            } else {
                throw e;
            }
        }
    }

    /**
     * 简单的get请求, 框架默认重试3次
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, null);
    }

    /**
     * 带参数的简单get请求, 超时和重试设置同{ com.bozhi.core.util.HttpClientUtil#doGet(java.lang.String)}
     */
    public static String doGet(String url, Map<String, String> map) throws Exception {
        return doGet(url, map, 0, 0, 0);
    }

    /**
     * 遇到NoHttpResponseException和UnknownHostException异常可额外重试次数
     * 每次重试任然走默认配置的重试3次
     */
    public static String doGet(String url, Map<String, String> map, int connectionRequestTimeout,
                               int connectTimeout, int socketTimeout, int retry) throws Exception {
        try {
            return doGet(url, map, connectionRequestTimeout, connectTimeout, socketTimeout);
        } catch (Exception e) {
            if (retry > 0 && (e instanceof UnknownHostException || e instanceof NoHttpResponseException)) {
                log.warn("请求[{}]失败, 剩余重试[{}]次", url, retry);
                --retry;
                return doGet(url, map, connectionRequestTimeout, connectTimeout, socketTimeout, retry);
            }
            throw e;
        }
    }

    public static String doGet(String url, Map<String, String> map, int connectionRequestTimeout,
                               int connectTimeout, int socketTimeout) throws Exception {

        HttpClientContext context = HttpClientContext.create();
        HttpGet httpGet;
        if (MapUtils.isNotEmpty(map)) {
            httpGet = new HttpGet(buildUri(url, map));
        } else {
            httpGet = new HttpGet(url);
        }

        connectionRequestTimeout = connectionRequestTimeout > 0 ? connectionRequestTimeout : DEFAULT_CONNECTION_REQUEST_TIMEOUT;
        connectTimeout = connectTimeout > 0 ? connectTimeout : DEFAULT_CONNECT_TIMEOUT;
        socketTimeout = socketTimeout > 0 ? socketTimeout : DEFAULT_SOCKET_TIMEOUT;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            logger.error("do get error ", e);
            throw e;
        } finally {
            close(context, httpGet, response);
        }
    }

    private static void close(HttpClientContext context, HttpGet httpGet, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
            if (context.getCookieStore() != null) {
                context.getCookieStore().clear();
            }
            if (httpGet != null) {
                httpGet.abort();
            }
        } catch (Exception e) {
            logger.error("close error", e);
        }
    }

    private static URI buildUri(String url, Map<String, String> map) throws URISyntaxException {

        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }

    /**
     * 通过url下载文件到指定的文件里面
     *
     * @param url
     * @param file
     */
    public static void downloadFile(String url, File file, int connectionRequestTimeout,
                                    int connectTimeout, int socketTimeout) {
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpGet = new HttpGet(url);

        connectionRequestTimeout = connectionRequestTimeout > 0 ? connectionRequestTimeout : DEFAULT_CONNECTION_REQUEST_TIMEOUT;
        connectTimeout = connectTimeout > 0 ? connectTimeout : DEFAULT_CONNECT_TIMEOUT;
        socketTimeout = socketTimeout > 0 ? socketTimeout : DEFAULT_SOCKET_TIMEOUT;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream is = entity.getContent();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[4096];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("do get error ", e);
        } finally {
            close(context, httpGet, response);
        }

    }
}
