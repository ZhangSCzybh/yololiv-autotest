package com.yolocast.qa.zsc.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangsc
 * @date 2022-05-01 上午11:38
 */
public class LoginUtil {

    //获取账号中心登录cookie
    public static String loginCookie1(String url, String eamil, String password) {
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse res = null;
        // 创建本地的HTTP内容
        try {
            try {
                String bodys = String.format("{\"eamil\":\"%s\",\"password\":\"%s\"}", eamil, password);
                System.out.println(bodys);
                // 创建一个post请求
                HttpPost post = new HttpPost(url);
                // 注入post数据
                post.setEntity(new StringEntity(bodys, "utf-8"));
                post.addHeader("Content-Type", "application/json;charset=utf-8");
                res = httpClient.execute(post, context);
                JSONObject response = JSONObject.parseObject(EntityUtils.toString(res.getEntity()));
                res.close();

                System.out.println(response);
                return response.getJSONObject("data").getString("token");
            } finally {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


/**
 * @Description: TODO methods
 * @param
 * @param url
 * @param eamil
 * @param password
 * @return java.lang.String
 * @author Zhangsc
 * @date 2024/6/7 21:43
 */
    //获取账号中心登录cookie
    public static String loginCookie(String url, String eamil, String password){
        //String param = "{\"email\":\"zhangsc@yunxi.tv\",\"password\":\"zsc123456\"}";
        //String body = String.format("{\"eamil\":\"%s\",\"password\":\"%s\"}", eamil, password);
        try {
            cn.hutool.json.JSONObject param = JSONUtil.createObj();
            param.put("email", eamil);
            param.put("password", password);
            String body = param.toString();
            String result = HttpUtil.post(url, body);
            //System.out.println(result);
            cn.hutool.json.JSONObject jsonresult = new cn.hutool.json.JSONObject(result);
            return (String) (new cn.hutool.json.JSONObject(jsonresult.get("data"))).get("token");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //TODO  获取账号中心登录cookie
    public static String loginCookie2(String url, String eamil, String password){
        //String param = "{\"email\":\"zhangsc@yunxi.tv\",\"password\":\"zsc123456\"}";
        //String body = String.format("{\"eamil\":\"%s\",\"password\":\"%s\"}", eamil, password);
        try {
            cn.hutool.json.JSONObject param = JSONUtil.createObj();
            param.put("email", eamil);
            param.put("password", password);
            String body = param.toString();
            String result = HttpUtil.post(url, body);
            //System.out.println(result);
            HttpRequest request = HttpUtil.createPost(url);
            request.form("identityType", "3")
                    .form("password", "zybh123456")
                    .form("username", "17858805099");
            HttpResponse response = request.execute();
            // 获取响应状态码
            int statusCode = response.getStatus();
            System.out.println(statusCode);
            Map<String, List<String>> headers = response.headers();
            String Token = response.header("Session-Token");
            System.out.println(Token);


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
