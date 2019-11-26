package com.example.redisdemo.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author wc
 */
public class ListsUtil {
    /**
     * 将一个集合插入目标集合的指定位置
     * 默认规则视频的插入
     *
     * @param target   目标集合
     * @param source   指定集合
     * @param position 指定位置
     * @param <T>      T
     * @return 替换后的目标集合
     */
    public static <T> List<T> replaceListElement(List<T> target, List<T> source, int position) {
        if (CollectionUtils.isEmpty(target)) {
            return source;
        }
        if (CollectionUtils.isEmpty(source)) {
            return target;
        }
        if (position > target.size()) {
            target.addAll(source);
            return target;
        }
        int step = position - 1;
        int pos = 0;
        int size = source.size();
        for (int i = 0; i < target.size(); i++) {
            if (i == step) {
                target.set(i, source.get(pos));
                pos++;
                if (pos == size) {
                    break;
                }
                step += position;
            }
            if (i == target.size() - 1 && pos != size) {
                target.addAll(source.subList(pos,size));
                break;
            }
        }
        return target;
    }
}
