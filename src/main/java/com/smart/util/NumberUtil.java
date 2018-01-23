package com.smart.util;

import java.text.DecimalFormat;

/**
 * @author: Smart
 * @date: 2018/01/23 16:53
 * @explain: 数字转化工具类
 */
public class NumberUtil {
    private static final DecimalFormat DF = new DecimalFormat("#");

    /**
     * 字符是否为数字
     *
     * @param c : 字符
     * @return
     */
    public static boolean isNumberic(char c) {
        if (c >= 48 && c <= 57) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是纯数字
     *
     * @param str : 字符串
     * @return
     */
    public static boolean isNumber(String str) {
        return isNumber(str, false);
    }

    /**
     * 判断字符串是否是纯数字
     *
     * @param str  : 字符串
     * @param trim : 是否去掉首尾的空格
     * @return
     */
    public static boolean isNumber(String str, boolean trim) {
        if (str == null || "".equals(str.trim()) || "null".equals(str)) {
            return false;
        }
        // 1. 判断是否是小数,去掉第一个小数点
        if (str.contains(".")) {
            str = str.replaceFirst("\\.", "");
        }

        if (trim) {
            str = str.trim();
        }
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (!isNumberic(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 科学计数法转为字符串
     *
     * @param scientific : 科学计数法
     * @return
     */
    public static String scientific2Str(String scientific) {
        return scientific2Str(Double.valueOf(scientific));
    }

    /**
     * 科学计数法转为字符串
     *
     * @param d : 科学计数法
     * @return
     */
    public static String scientific2Str(double d) {
        return DF.format(d);
    }

    public static void main(String[] args) {
        System.out.println(scientific2Str("1.235E0003"));
    }
}
