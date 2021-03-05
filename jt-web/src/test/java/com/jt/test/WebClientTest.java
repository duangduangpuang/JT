package com.jt.test;

import com.jt.pojo.Item;
import com.jt.util.HttpClientService;
import com.jt.utils.ObjectMapperUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest()
public class WebClientTest {

    @Test
    public void testHttpDoGet(){
        HttpClient httpClient = HttpClients.createDefault();
        String url = "https://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                HttpEntity entity = httpResponse.getEntity();
                String json = EntityUtils.toString(entity,"utf-8");
                System.out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private HttpClientService httpClientService;

    @Test
    public void testParams(){
        String url = "https://www.baidu.com";
        Map<String,String> params = new HashMap<>();
        params.put("id","123");
        params.put("name","tomcat");
        String result = httpClientService.doGet(url, params);
        System.out.println(result);


    }


    @Test
    public void testFindItemById(){
        String url = "http://manage.jt.com/web/item/findItemById";
        Map<String,String> params = new HashMap<>();
        params.put("itemId","562379");
        String result = httpClientService.doGet(url, params);
        Item item = ObjectMapperUtil.toObj(result, Item.class);
        System.out.println(item);
    }

}
