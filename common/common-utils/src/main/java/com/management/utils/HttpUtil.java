package com.management.utils;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 11:58 2018/12/4
 * @ Description：模拟浏览器请求
 * @ Modified By：
 * @Version: 1.0.0
 */
public class HttpUtil {

    /**
     * 模拟浏览器请求
     * @param httpUrl
     * @param params
     * @return
     */
    public static String sendHttpRequest(String httpUrl, Map<String, String> params)throws Exception{
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 设置请求的方式
        conn.setRequestMethod("POST");
        // 设置需要输出0
        conn.setDoOutput(true);
        // 判断是否有参数
        if(params != null && params.size() >0 ){
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String, String> entry:params.entrySet()){
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            conn.getOutputStream().write(sb.substring(1).toString().getBytes("utf-8"));
        }
        // 发送请求到服务器
        conn.connect();
        // 获取远程响应内容
        String responseContent = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        conn.disconnect();
        return responseContent;
    }

    public static void sendHttpRequest(String httpURL, String jesssionId)throws Exception{
        // 建立连接对象
        URL url = new URL(httpURL);
        // 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式
        conn.setRequestMethod("POST");
        // 设置需要输出
        conn.setDoOutput(true);
        conn.setRequestProperty("Cookie", "JSESSIONID=" + jesssionId);
        // 发送请求
        conn.connect();
        conn.getInputStream();
        conn.disconnect();
    }
}
