package com.knowincloud.third.api.toutiao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowincloud.po.entity.news.Toutiao;
import com.knowincloud.sm.gateway.aliyun.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/3/22.
 */
public class KicToutiaoApi {
    public static final JSONArray pullNews(){
        String host = "http://toutiao-ali.juheapi.com";
        String path = "/toutiao/index";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE 0b4cc9f202234a81b22507209358c05f");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("type", "type");

        HttpResponse response;
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            response = HttpUtils.doGet(host, path, method, headers, querys);
            String entityString=EntityUtils.toString(response.getEntity());
            JSONObject object=JSONObject.parseObject(entityString);
            JSONObject result=object.getJSONObject("result");
            JSONArray array=result.getJSONArray("data");
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Toutiao> toToutiaoList(JSONArray array){
        List<Toutiao> toutiaos=new ArrayList<>();
        for (int i=0;i<array.size();i++){
            JSONObject object=array.getJSONObject(i);
            Toutiao toutiao=new Toutiao();
            if(object.containsKey("uniquekey")){
                toutiao.setUniquekey(object.getString("uniquekey"));
            }
            if(object.containsKey("title")){
                toutiao.setTitle(object.getString("title"));
            }
            if(object.containsKey("date")){
                toutiao.setDate(object.getString("date"));
            }
            if(object.containsKey("category")){
                toutiao.setCategory(object.getString("category"));
            }
            if(object.containsKey("author_name")){
                toutiao.setAuthorName(object.getString("author_name"));
            }
            if(object.containsKey("url")){
                toutiao.setUrl(object.getString("url"));
            }
            if(object.containsKey("thumbnail_pic_s")){
                toutiao.setThumbnailPicS(object.getString("thumbnail_pic_s"));
            }
            if(object.containsKey("thumbnail_pic_s02")){
                toutiao.setThumbnailPicS02(object.getString("thumbnail_pic_s02"));
            }
            if(object.containsKey("thumbnail_pic_s03")){
                toutiao.setThumbnailPicS03(object.getString("thumbnail_pic_s03"));
            }
            toutiaos.add(toutiao);
        }
        return toutiaos;
    }
    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(toToutiaoList(pullNews())));
    }
}
