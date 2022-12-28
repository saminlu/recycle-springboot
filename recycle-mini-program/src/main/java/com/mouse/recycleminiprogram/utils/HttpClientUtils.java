package com.mouse.recycleminiprogram.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author ：yangwang
 * @description：用于HttpClient请求
 * @email 1187493980@qq.com
 *        shuiyuetianwy@gmaill.com
 * @date ：2021/11/8 8:56
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    //重定向code
    public static final List<String> REDIRECT_CODE = new ArrayList<String>(){{
        add("300");add("301");add("302");add("303");add("305");add("307");
    }};

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendGet(String url) throws Exception {
        return sendGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendGet(String url, Map<String, Object> params) throws Exception {
        return sendGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendGet(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        logger.info("httpClient-get请求参数为--url:{};headers:{};params:{}",url,headers,params);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if((null!=params)&&(!params.isEmpty())) {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendPost(String url) throws Exception {
        return sendPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @param formOrJson form或json传递参数(0-json;1-form)
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendPost(String url, Map<String, Object> params,Integer formOrJson) throws Exception {
        return sendPost(url, null, params,formOrJson);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @param formOrJson form或json传递参数(0-json;1-form)
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendPost(String url, Map<String, String> headers, Map<String, Object> params,Integer formOrJson) throws Exception {
        logger.info("httpClient-post请求参数为--url:{};headers:{};params:{};formOrJson:{}",url,headers,params,formOrJson);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        // 设置请求头
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        packageHeader(headers, httpPost);

        // 封装请求参数
        if((null!=formOrJson)&&(1 == formOrJson)){//form传参
            packageParamOfForm(params, httpPost);
        }else{//json传参
            packageParamOfJson(params, httpPost);
        }

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendPut(String url) throws Exception {
        return sendPut(url,null,null);
    }


    /**
     * 发送put请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @param formOrJson form或json传递参数(0-json;1-form)
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendPut(String url, Map<String, Object> params,Integer formOrJson) throws Exception {
        logger.info("httpClient-put请求参数为--url:{};params:{}",url,params);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        // 封装请求参数
        if((null!=formOrJson)&&(1 == formOrJson)){//form传参
            packageParamOfForm(params, httpPut);
        }else{//json传参
            packageParamOfJson(params, httpPut);
        }

        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if((null!=params)&&(!params.isEmpty())) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数-form方式
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParamOfForm(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if((null!=params)&&(!params.isEmpty())) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Description: 封装请求参数-json方式
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParamOfJson(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if((null!=params)&&(!params.isEmpty())) {
            httpMethod.setEntity(new StringEntity(JSON.toJSONString(params)));
//            httpMethod.setEntity(new ByteArrayEntity(JSON.toJSONString(params).getBytes()));
        }
    }


    /**
     * Description: 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return Map<String,String>
     * @throws Exception
     */
    public static Map<String,String> getHttpClientResult(CloseableHttpResponse httpResponse,
                                                         CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {

        Map<String,String> result = new HashMap<String,String>();
        result.put("statusCode",String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));

        // 执行请求
        httpResponse = httpClient.execute(httpMethod);
        logger.info("httpClient请求相应结果为--httpResponse:{}",httpResponse);

        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            result.put("statusCode",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            result.put("content",(null != httpResponse.getEntity())?EntityUtils.toString(httpResponse.getEntity(), ENCODING):"");
            //判定重定向
            if(REDIRECT_CODE.contains(String.valueOf(httpResponse.getStatusLine().getStatusCode()))){
                Header locationHeader = httpResponse.getFirstHeader("Location");
                result.put("location",(null!=locationHeader)?locationHeader.getValue():"");
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("httpClient请求相应结果为--responseResultMap:{}",result);
            }
        }).start();
        return result;
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "https://devapi.qweather.com/v7/weather/now";
        Map<String,Object> paramMap = new HashMap<String,Object>(){{
            put("location","101010100");
            put("key","46240bab14e24d84ba69afef90f1d435");
            put("lang","zh");
        }};

        Map<String,String> sr=HttpClientUtils.sendGet(url,null,paramMap);
        System.out.println(sr);
    }
}