package com.smart.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: SMA
 * @Date: 2017-08-11 18:32
 * @Explain: 日期转化工具类
 */
public class DateUtil {

    private static final char YAER = 'y';  // 年
    private static final char MONTH = 'M'; // 月
    private static final char DAY = 'd';  // 天
    private static final char HOUR = 'H';  // 时
    private static final char MINUTE = 'm';  // 分
    private static final char SECOND = 's';  // 秒

    /**
     * 是否是数字
     *
     * @param c
     * @return
     */
    private static boolean isNumberic(char c) {
        if (c >= 48 && c <= 57) {
            return true;
        }
        return false;
    }

    /**
     * 转化为时间格式模板
     *
     * @param str
     * @return
     */
    public static String template(String str) {
        assert str != null & !"".equals(str.trim());
        // 识别str的类型
        StringBuilder builder = new StringBuilder();
        int type = 0;
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (isNumberic(c)) {
                if (type < 4) {
                    builder.append(YAER);
                    type++;
                } else if (type < 6) {
                    builder.append(MONTH);
                    type++;
                } else if (type < 8) {
                    builder.append(DAY);
                    type++;
                } else if (type < 10) {
                    builder.append(HOUR);
                    type++;
                } else if (type < 12) {
                    builder.append(MINUTE);
                    type++;
                } else if (type < 14) {
                    builder.append(SECOND);
                    type++;
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 将字符串转为绝对毫秒
     * 类似 yyyy-MM-dd HH:mm:ss格式的时间转为时间戳
     *
     * @param str   : date
     * @param limit : 是否限制精确到秒
     * @return
     * @throws Exception
     */
    public synchronized static long date2Time(String str, boolean limit) throws Exception {
        if (limit && (str == null || str.length() < 14)) {
            throw new Exception("无法格式化该类型的日期数据, Time=" + str);
        }
        SimpleDateFormat format = new SimpleDateFormat(template(str));
        return format.parse(str).getTime();
    }

    /**
     * 将字符串转为绝对毫秒
     * 类似 yyyy-MM-dd HH:mm:ss格式的时间转为时间戳
     *
     * @param str : 字符串
     * @return
     * @throws Exception
     */
    public synchronized static long date2Time(String str) throws Exception {
        return date2Time(str, true);
    }

    /**
     * 将绝对毫秒转为字符串
     *
     * @param time : 绝对秒
     * @return
     */
    public synchronized static String time2Date(long time) {
        return time2Date(time, "yyyyMMddHHmmss");
    }

    /**
     * 将绝对毫秒转为字符串
     *
     * @param time   : 绝对秒
     * @param format : 格式
     * @return
     */
    public synchronized static String time2Date(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }
}
