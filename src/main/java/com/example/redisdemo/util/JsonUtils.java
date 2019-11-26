package com.example.redisdemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
    public static final String JSON_DESERIALIZE_ERROR_MSG = "Json反序列化出错";
    public static final String JSON_SERIALIZE_ERROR_MSG = "Json序列化出错";
    private static final ObjectMapper OBJECT_MAPPER;

    public JsonUtils() {
    }

    public static Object json2Obj(String jsonStr) {
        return json2Obj(jsonStr, Object.class);
    }

    public static <T> T json2Obj(String jsonStr, Class<T> clazz) {
        if (jsonStr == null) {
            return null;
        } else {
            try {
                return OBJECT_MAPPER.readValue(jsonStr, clazz);
            } catch (Exception var3) {
                throw new RuntimeException("Json反序列化出错", var3);
            }
        }
    }

    public static void writeJson2Obj2OutputStream(Object content, OutputStream outputStream) {
        if (!Objects.isNull(content)) {
            try {
                OBJECT_MAPPER.writeValue(outputStream, content);
            } catch (Exception var3) {
                throw new RuntimeException("Json序列化出错", var3);
            }
        }
    }

    public static <T> T json2Obj(String content, Class<T> clazzItem, Class... classes) {
        if (StringUtils.isBlank(content)) {
            return null;
        } else {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);

            try {
                return OBJECT_MAPPER.readValue(content, javaType);
            } catch (Exception var5) {
                throw new RuntimeException("Json反序列化出错", var5);
            }
        }
    }

    public static TypeFactory getTypeFactory() {
        return OBJECT_MAPPER.getTypeFactory();
    }

    public static <T> JsonResult<T> parseJsonResult(String content, Class<T> clazzItem, Class... classes) {
        if (StringUtils.isBlank(content)) {
            return null;
        } else {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);
            JavaType finalJavaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(JsonResult.class, new JavaType[]{javaType});

            try {
                return (JsonResult)OBJECT_MAPPER.readValue(content, finalJavaType);
            } catch (Exception var6) {
                throw new RuntimeException("Json反序列化出错", var6);
            }
        }
    }

    public static <T> T json2Obj(String content, JavaType javaType) {
        if (StringUtils.isBlank(content)) {
            return null;
        } else {
            try {
                return OBJECT_MAPPER.readValue(content, javaType);
            } catch (Exception var3) {
                throw new RuntimeException("Json反序列化出错", var3);
            }
        }
    }

    public static <K, V> Map<K, V> json2Map(String content, Class<K> keyCls, Class<V> valueCls) {
        Map<String, Object> jsonMap = (Map)json2Obj(content, Map.class);
        if (jsonMap == null) {
            return null;
        } else {
            Map<K, V> result = Maps.newHashMap();
            jsonMap.forEach((key, value) -> {
                K keyObj = json2Obj(key, keyCls);
                V valueObj = json2Obj(value.toString(), valueCls);
                result.put(keyObj, valueObj);
            });
            return result;
        }
    }

    public static String obj2Json(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception var2) {
            throw new RuntimeException("Json序列化出错", var2);
        }
    }

    static {
        OBJECT_MAPPER = (new ObjectMapper()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule()).registerModule(new GuavaModule()).setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
