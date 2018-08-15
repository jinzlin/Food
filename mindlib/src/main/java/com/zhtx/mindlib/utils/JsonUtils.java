package com.zhtx.mindlib.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;

/**
 * 作者: ljz.
 * @date 2017/11/20.
 * 描述：JSONObject工具类
 */

public class JsonUtils {
    public static String getString(JSONObject jsonObject, String key){
        return getString(jsonObject, key, "");
    }

    public static String getString(JSONObject jsonObject, String key, String sDefault){
        try{
            return jsonObject.getString(key);
        }catch (JSONException e){
            L.e("JSONUtlis no find " + key + " ------ jsonObject = " + jsonObject);
            return sDefault;
        }
    }

    public static int getInt(JSONObject jsonObject, String key){
        return getInt(jsonObject, key, 0);
    }

    public static int getInt(JSONObject jsonObject, String key, int iDefault){
        try{
            return jsonObject.getInt(key);
        }catch (JSONException e){
            L.e("JSONUtlis no find " + key + " ------ jsonObject = " + jsonObject);
            return iDefault;
        }
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String key){
        return getJSONArray(jsonObject, key, null);
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray iDefault){
        try{
            return jsonObject.getJSONArray(key);
        }catch (JSONException e){
            L.e("JSONUtlis no find " + key + " ------ jsonObject = " + jsonObject);
            return iDefault;
        }
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String data){
        try {
            return jsonObject.getJSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getJSONObject(String data){
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject putString(JSONObject jsonObject, String key, String value){
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * hashMap转JSON
     *
     * @param hashMap
     * @return
     */
    public static JSONStringer createJSON(HashMap<String, Object> hashMap) {
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
            for (HashMap.Entry<String, Object> entry : hashMap.entrySet()) {
                if (null != entry && null != entry.getKey()) {
                    jsonText.key(entry.getKey());   // 获取key
                    String value;
                    if (null != entry.getValue()){
                        value = entry.getValue().toString();
                        jsonText.value(value.replaceAll("'", "’")); // value不为null 英文引号替换成中文引号
                    } else {
                        jsonText.value("");
                    }
                }
            }
            jsonText.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText;
    }
}
