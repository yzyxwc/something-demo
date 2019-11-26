package com.example.redisdemo.util.redis;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author wc
 */
public final class RedisUtil {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public RedisUtil() {
    }

    public static byte[] toByteArray(Serializable id) {
        return id.toString().getBytes();
    }

    public static byte[] currentTs() {
        return toByteArray(System.currentTimeMillis());
    }

    public static byte[] toByteArray(Boolean value) {
        return value == null ? new byte[0] : (value ? "1" : "0").getBytes();
    }

    public static byte[] toByteArray(Byte value) {
        return value == null ? new byte[0] : Byte.toString(value).getBytes();
    }

    public static byte[] toByteArray(Short value) {
        return value == null ? new byte[0] : Short.toString(value).getBytes();
    }

    public static byte[] toByteArray(Integer value) {
        return value == null ? new byte[0] : Integer.toString(value).getBytes();
    }

    public static byte[] toByteArray(Long value) {
        return value == null ? new byte[0] : Long.toString(value).getBytes();
    }

    public static byte[] toByteArray(Date value) {
        return value == null ? null : Long.toString(value.getTime()).getBytes();
    }

    public static byte[] toByteArray(String value) {
        return value == null ? new byte[0] : value.getBytes(DEFAULT_CHARSET);
    }

    public static byte[] toByteArray6(BigDecimal value) {
        return value == null ? new byte[0] : toByteArray(value.movePointRight(6).intValue());
    }

    public static BigDecimal byteArrayToBigDecimal6(byte[] b) {
        int intValue = byteArrayToInt(b);
        return (new BigDecimal(intValue)).movePointLeft(6);
    }

    public static byte[][] toByteArray(int[] value) {
        byte[][] result = new byte[value.length][];

        for(int i = 0; i < value.length; ++i) {
            result[i] = toByteArray(value[i]);
        }

        return result;
    }

    public static byte[][] toByteArray(List<Integer> list) {
        byte[][] result = new byte[list.size()][];

        for(int i = 0; i < list.size(); ++i) {
            result[i] = toByteArray((Integer)list.get(i));
        }

        return result;
    }

    public static byte[][] ListBytetoByteArray(List<byte[]> list) {
        byte[][] result = new byte[list.size()][];

        for(int i = 0; i < list.size(); ++i) {
            result[i] = toByteArray((Serializable)list.get(i));
        }

        return result;
    }

    public static byte[][] toByteArray(long[] value) {
        byte[][] result = new byte[value.length][];

        for(int i = 0; i < value.length; ++i) {
            result[i] = toByteArray(value[i]);
        }

        return result;
    }

    public static Integer byteArrayToInt(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Integer.parseInt(new String(b));
    }

    public static Long byteArrayToLong(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Long.parseLong(new String(b));
    }

    public static Short byteArrayToShort(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Short.parseShort(new String(b));
    }

    public static Byte byteArrayToByte(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : Byte.parseByte(new String(b));
    }

    public static Boolean byteArrayToBoolean(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : "1".equals(new String(b));
    }

    public static String byteArrayToStr(byte[] b) {
        return ArrayUtils.isEmpty(b) ? null : new String(b, DEFAULT_CHARSET);
    }

    public static java.sql.Date byteArrayToDate(byte[] bytesValue) {
        long longValue = byteArrayToLong(bytesValue);
        return new java.sql.Date(longValue);
    }

    public static int[] bytesSetToIntArray(Set<byte[]> bytesSet) {
        if (bytesSet != null && !bytesSet.isEmpty()) {
            int[] userIdArr = new int[bytesSet.size()];
            int count = 0;

            for(Iterator var3 = bytesSet.iterator(); var3.hasNext(); ++count) {
                byte[] userIdBytes = (byte[])var3.next();
                userIdArr[count] = byteArrayToInt(userIdBytes);
            }

            return userIdArr;
        } else {
            return null;
        }
    }

    public static List<Integer> bytesSetToIntList(Set<byte[]> bytesSet) {
        if (bytesSet != null && !bytesSet.isEmpty()) {
            List<Integer> userIdArr = new ArrayList(bytesSet.size());
            Iterator var2 = bytesSet.iterator();

            while(var2.hasNext()) {
                byte[] userIdBytes = (byte[])var2.next();
                userIdArr.add(byteArrayToInt(userIdBytes));
            }

            return userIdArr;
        } else {
            return Lists.newArrayList();
        }
    }

    public static Long[] bytesSetToLongArray(Set<byte[]> bytesSet) {
        if (CollectionUtils.isEmpty(bytesSet)) {
            return null;
        } else {
            Long[] idArr = new Long[bytesSet.size()];
            int count = 0;

            for(Iterator var3 = bytesSet.iterator(); var3.hasNext(); ++count) {
                byte[] userIdBytes = (byte[])var3.next();
                idArr[count] = byteArrayToLong(userIdBytes);
            }

            return idArr;
        }
    }

    public static List<Long> bytesSetToLongList(Set<byte[]> bytesSet) {
        if (CollectionUtils.isEmpty(bytesSet)) {
            return Lists.newArrayList();
        } else {
            List<Long> idList = new ArrayList(bytesSet.size());
            Iterator var2 = bytesSet.iterator();

            while(var2.hasNext()) {
                byte[] userIdBytes = (byte[])var2.next();
                idList.add(byteArrayToLong(userIdBytes));
            }

            return idList;
        }
    }

    public static List<String> bytesSetToStringList(Set<byte[]> bytesSet) {
        if (CollectionUtils.isEmpty(bytesSet)) {
            return new ArrayList();
        } else {
            List<String> strings = new ArrayList(bytesSet.size());
            Iterator var2 = bytesSet.iterator();

            while(var2.hasNext()) {
                byte[] bytes = (byte[])var2.next();
                strings.add(byteArrayToStr(bytes));
            }

            return strings;
        }
    }

    public static String[] bytesSetToStringArray(Set<byte[]> bytesSet) {
        if (bytesSet != null && !bytesSet.isEmpty()) {
            String[] userIdArr = new String[bytesSet.size()];
            int count = 0;

            for(Iterator var3 = bytesSet.iterator(); var3.hasNext(); ++count) {
                byte[] userIdBytes = (byte[])var3.next();
                userIdArr[count] = byteArrayToStr(userIdBytes);
            }

            return userIdArr;
        } else {
            return null;
        }
    }

    public static List<Long> stringSetToLongList(Set<String> stringSet) {
        if (CollectionUtils.isEmpty(stringSet)) {
            return null;
        } else {
            List<Long> idList = new ArrayList(stringSet.size());
            Iterator var2 = stringSet.iterator();

            while(var2.hasNext()) {
                String s = (String)var2.next();
                idList.add(Long.valueOf(s));
            }

            return idList;
        }
    }

    public static List<String> stringSetToStringList(Set<String> stringSet) {
        if (CollectionUtils.isEmpty(stringSet)) {
            return new ArrayList();
        } else {
            List<String> idList = new ArrayList(stringSet.size());
            Iterator var2 = stringSet.iterator();

            while(var2.hasNext()) {
                String s = (String)var2.next();
                idList.add(String.valueOf(s));
            }

            return idList;
        }
    }
}
