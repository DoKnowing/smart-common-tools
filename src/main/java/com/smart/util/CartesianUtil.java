package com.smart.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: SMA
 * @date: 2018-01-16 11:50
 * @explain:
 */
public class CartesianUtil {

    /**
     * 笛卡尔积
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> cartesian(List<List<T>> list) {
        List<List<T>> result = new ArrayList<>();
        cartesian(list, result, 0, new ArrayList<T>());
        return result;
    }

    /**
     * 递归实现笛卡尔积
     *
     * @param dimValue : 原始数组
     * @param result   : 结果数组
     * @param layer    : 层数
     * @param curList  : 每次笛卡尔积的结果
     * @param <T>
     */
    private static <T> void cartesian(List<List<T>> dimValue, List<List<T>> result, int layer, List<T> curList) {
        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                cartesian(dimValue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(curList);
                    list.add(dimValue.get(layer).get(i));
                    cartesian(dimValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(curList);
                    list.add(dimValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<String> l1 = new ArrayList<>();
        l1.add(1 + "");
        l1.add(2 + "");
        l1.add(3 + "");

        List<String> l2 = new ArrayList<>();
        l2.add(4 + "");
        l2.add(5 + "");
        l2.add(6 + "");

        List<String> l3 = new ArrayList<>();
        l3.add(7 + "");
        l3.add(8 + "");
        l3.add(9 + "");
        List<List<String>> listList = Arrays.asList(l1, l2, l3);

        List c = cartesian(listList);
        System.out.println(c);
    }
}
