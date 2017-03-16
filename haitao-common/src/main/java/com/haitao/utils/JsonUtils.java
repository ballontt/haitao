package com.haitao.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by ballontt on 2017/3/9.
 */
public class JsonUtils{
    private final static ObjectMapper MAPPER = new ObjectMapper();

//    将java对象转换为json字符串
    public static String objectToJson(Object object) {
        try {
            String string = MAPPER.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    将json结果转换为Java对象
    public static <T> T jsonToObject(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData,beanType);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    将json结果转换为pojo对象list
    public static <T>List<T> jsonToList(String jsonData, Class<T>beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
