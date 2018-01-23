package com.smart.pattern.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sma
 * @date: 2018-01-16 11:46
 * @explain: Sunday算法是从前往后匹配，在匹配失败时关注的是文本串中参加匹配的最末位字符的下一位字符。
 * 1. 如果该字符没有在模式串中出现则直接跳过，即移动位数 = 匹配串长度 + 1；
 * 2. 否则，其移动位数 = 模式串中最右端的该字符到末尾的距离+1。
 */
public class SundayMatcher {

    /**
     * 完全匹配
     *
     * @param text 内容
     * @param word 关键词
     * @return
     */
    public static List<Integer> matchAll(String text, String word) {
        return matchAll(text, word, false);
    }

    /**
     * 完全匹配，可设置大小写模糊匹配
     *
     * @param text
     * @param word
     * @param ignoreCase
     * @return
     */
    public static List<Integer> matchAll(String text, String word, boolean ignoreCase) {
        if (text == null || word == null || text.trim().isEmpty() || word.trim().isEmpty()) {
            return null;
        }
        if (ignoreCase) {
            text = text.toLowerCase();
            word = word.toLowerCase();
        }

        List<Integer> posts = new ArrayList<Integer>();
        int post = 0;
        int post_text = 0;// text游标
        int post_word = 0;// word游标

        int textLen = text.length();
        int wordLen = word.length();
        if (textLen < wordLen) {
            return null;
        } else if (textLen == wordLen && text.equals(word)) {// 完全一样
            posts.add(0);
            return posts;
        }

        while (post_text < textLen) {
            if (word.charAt(post_word) == text.charAt(post_text)) {// 字符一样
                post_word++;
                post_text++;
            } else {
                if ((post_text - post_word + wordLen) <= (textLen - 1)) {
                    // 如果不匹配，则找到参加匹配的最末字段的下一个字符，即post_text - post_word + wordLen
                    post = word.lastIndexOf(text.charAt(post_text - post_word + wordLen));
                } else {
                    break;
                }

                if (post == -1) {// 如果没有找到，则移动wordLen + 1长度
                    post_text = post_text - post_word + wordLen + 1;
                    post_word = 0;
                } else {// 如果找到，则移动(wordLen - post)长度
                    post_text = post_text - post_word + wordLen - post;
                    post_word = 0;
                }
            }

            if (post_word == wordLen) {// 找到word，word游标置0
                posts.add(post_text - wordLen);
                post_text = post_text - wordLen + 1;
                post_word = 0;
            }
        }
        if (posts.isEmpty()) {
            return null;
        }
        return posts;
    }

    /**
     * 匹配是否存在关键词
     *
     * @param text 文本
     * @param word 关键词
     * @return
     */
    public static boolean match(String text, String word) {
        return match(text, word, false);
    }

    /**
     * 匹配是否存在关键词，可设置大小写模糊匹配
     *
     * @param text
     * @param word
     * @param ignoreCase
     * @return
     */
    public static boolean match(String text, String word, boolean ignoreCase) {
        if (text == null || word == null || text.trim().isEmpty() || word.trim().isEmpty()) {
            return false;
        }
        if (ignoreCase) {
            text = text.toLowerCase();
            word = word.toLowerCase();
        }

        int post = 0;
        int post_text = 0;// text游标
        int post_word = 0;// word游标

        int textLen = text.length();
        int wordLen = word.length();
        if (textLen < wordLen) {
            return false;
        } else if (textLen == wordLen) {
            return text.equals(word);
        }

        while (post_text < textLen) {
            if (word.charAt(post_word) == text.charAt(post_text)) {
                post_word++;
                post_text++;
            } else {
                if ((post_text - post_word + wordLen) <= (textLen - 1)) {
                    // 如果不匹配，则找到参加匹配的最末字段的下一个字符，即post_text + wordLen
                    post = word.lastIndexOf(text.charAt(post_text + wordLen));
                } else {
                    break;
                }

                if (post == -1) {// 如果没有找到，则移动wordLen + 1长度
                    post_text = post_text - post_word + wordLen + 1;
                    post_word = 0;
                } else {// 如果找到，则移动(wordLen - post)长度
                    post_text = post_text - post_word + wordLen - post;
                    post_word = 0;
                }
            }

            if (post_word == wordLen) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(matchAll("substring searching", "search", true));

        System.out.println(matchAll("天气天气好个好天气", "天气好", true));
        System.out.println(matchAll("天天天天天天天天天天天", "天天", true));
        System.out.println(match("天天天天天天天天天天天", "天天", true));
    }
}
