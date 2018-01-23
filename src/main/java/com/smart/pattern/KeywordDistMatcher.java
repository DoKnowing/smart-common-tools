package com.smart.pattern;


import com.smart.pattern.basic.SundayMatcher;
import com.smart.util.CartesianUtil;

import java.util.*;

/**
 * @author: SMA
 * @date: 2018-01-16 10:48
 * @explain:
 */
public class KeywordDistMatcher {

    private static class Keyword {

        private String token;// 关键词
        private int position;// 位置，与开始位置一致
        private int startOffset;// 开始位置
        private int endOffset;// 结束位置

        public Keyword(String token, int position) {
            this.token = token;
            this.position = position;
            this.startOffset = position;
            this.endOffset = position + token.length();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getStartOffset() {
            return startOffset;
        }

        public void setStartOffset(int startOffset) {
            this.startOffset = startOffset;
        }

        public int getEndOffset() {
            return endOffset;
        }

        public void setEndOffset(int endOffset) {
            this.endOffset = endOffset;
        }

        @Override
        public String toString() {
            return "Keyword{token=" + token + ",start_offset=" + startOffset + ",end_offset=" + endOffset + ",position=" + position + "}";
        }
    }

    /**
     * 匹配关键词的距离，默认不会忽略大小写
     *
     * @param text
     * @param words
     * @param dist
     * @return
     */
    public static boolean distMatch(String text, HashSet<String> words, int dist) {
        return distMatch(text, words, dist, false);
    }

    /**
     * 默认过滤叠词
     *
     * @param text
     * @param words
     * @param dist
     * @param ignoreCase
     * @return
     */
    public static boolean distMatch(String text, HashSet<String> words, int dist, boolean ignoreCase) {
        return distMatch(text, words, dist, ignoreCase, true);
    }

    /**
     * 匹配关键词的距离
     *
     * @param text        : 文本
     * @param words       : 关键词组
     * @param dist        : 最长距离
     * @param reiterative : 是否包含叠词:  如: 今天气  匹配关键词 今天|天气,默认会被过滤
     * @return
     */
    public static boolean distMatch(String text, HashSet<String> words, int dist, boolean ignoreCase, boolean reiterative) {
        boolean flag = false;
        if (text == null || words == null || text.isEmpty() || words.isEmpty()) {
            return flag;
        }
        HashMap<String, List<Keyword>> tokenMap = new HashMap<>();
        for (String word : words) {
            // 匹配位置
            List<Integer> ps = SundayMatcher.matchAll(text, word, ignoreCase);
            // 如果关键词未被匹配中，直接返回false
            if (ps == null) {
                return flag;
            }
            // 生成token
            List<Keyword> tokens = new ArrayList<>();
            for (int post : ps) {
                tokens.add(new Keyword(word, post));
            }
            tokenMap.put(word, tokens);
        }
        return distMatch(tokenMap, dist, reiterative);
    }

    /**
     * 根据关键词分组, 匹配关键词的距离
     *
     * @param tokens : 关键词的位置信息
     * @param dist   : 距离
     * @return
     */
    private static boolean distMatch(Map<String, List<Keyword>> tokens, int dist, boolean reiterative) {
        // 1. 不考虑字符长度的问题
        List<List<Keyword>> tokensList = new ArrayList<>();
        for (Map.Entry<String, List<Keyword>> entry : tokens.entrySet()) {
            tokensList.add(entry.getValue());
        }
        // 2. 做笛卡尔积，只有一组满足该距离即可
        List<List<Keyword>> cartesianList = CartesianUtil.cartesian(tokensList);
        for (List<Keyword> list : cartesianList) {
            // 至少一组满足即可
            if (dist(list.toArray(new Keyword[0]), dist, reiterative)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 匹配距离
     *
     * @param dist
     * @param posts
     * @return
     */
    private static boolean dist(Keyword[] posts, int dist, boolean reiterative) {
        // 0. 特殊判断
        if (posts == null || posts.length < 1) {
            return false;
        } else if (posts.length == 1) {
            return true;
        }

        // 1. 排序，插入排序，位置降序
        Keyword tmp;
        for (int i = 1; i < posts.length; i++) {
            for (int j = i; j > 0; j--) {
                if (posts[j].position > posts[j - 1].position) {
                    tmp = posts[j - 1];
                    posts[j - 1] = posts[j];
                    posts[j] = tmp;
                }
            }
        }

        // 2. 做差，同时需要去掉关键词的长度
        for (int i = 1; i < posts.length; i++) {
            int d = (posts[i - 1].position - posts[i].position - (posts[i].endOffset - posts[i].startOffset));
            if ((reiterative && d < 0) || d > dist) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String text = "你好,今天气是个好天气,但是有些人喜欢开挂,比如55开,打游戏需要的就是操作,不能开挂";
        HashSet<String> words = new HashSet<>(Arrays.asList("今天", "天气"));
        System.out.println(distMatch(text, words, -1, false, false));
    }
}
