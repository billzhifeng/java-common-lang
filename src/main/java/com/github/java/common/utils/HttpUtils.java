package com.github.java.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

/**
 * @Description HttpUtils.java
 * @date 2017年7月3日 下午5:14:58
 */
public class HttpUtils {

    public static String readRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    return null;
                }
            }
        }
        String requestBody = stringBuilder.toString();
        return requestBody;
    }

    /**
     * 构造支持https证书的HttpClient
     * 
     * @param clientCert 客户端证书，不需要传null（是否需要证书取决于服务器）
     * @param clientPass 客户端证书密码
     * @param serverCert 服务端证书，不需要传null（不需要服务器证书时则自动信任服务器）
     * @param serverPass 服务端证书密码
     * @return
     */
    public static CloseableHttpClient createHttpClientWithCert(String clientCert, String clientPass, String serverCert,
                                                               String serverPass) {
        InputStream keyStoreInput = null;
        InputStream trustStoreInput = null;
        KeyStore keyStore = null;
        KeyStore trustStore = null;
        //导入证书
        try {
            if (StringUtils.isNotBlank(clientCert)) {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStoreInput = ClassLoader.getSystemResourceAsStream(clientCert);
                keyStore.load(keyStoreInput, clientPass.toCharArray());
            }
            if (StringUtils.isNotBlank(serverCert)) {
                trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStoreInput = ClassLoader.getSystemResourceAsStream(serverCert);
                trustStore.load(trustStoreInput, serverPass.toCharArray());
            }
        } catch (Throwable t) {
            throw new RuntimeException("input KeyStore  fail", t);
        }
        SSLContext sslcontext = null;
        SSLContextBuilder sslContextBuilder = null;
        try {
            sslContextBuilder = SSLContexts.custom();
            if (keyStore != null) {
                sslContextBuilder = sslContextBuilder.loadKeyMaterial(keyStore, clientPass.toCharArray());
            }
            if (trustStore != null) {
                sslContextBuilder = sslContextBuilder.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy());
            }
            sslcontext = sslContextBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException("key store fail", e);
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf).register("http", PlainConnectionSocketFactory.INSTANCE).build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();// 小数据网络包
        connManager.setDefaultSocketConfig(socketConfig);
        CookieStore cookieStore = new BasicCookieStore();
        RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true).build();

        CloseableHttpClient httpclient;
        httpclient = HttpClients.custom().setConnectionManager(connManager).setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(defaultRequestConfig).evictExpiredConnections().setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
    }

    public static RequestConfig getProxyRequestConfig(String proxyIp, int proxyPort) {
        HttpHost proxy = new HttpHost(proxyIp, proxyPort);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        return config;
    }

}
