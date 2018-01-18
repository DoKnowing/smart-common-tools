package com.smart.parser.interfaces;

/**
 * @author: Smart
 * @date: 2018/1/18 16:06
 */
public abstract class SmartTxtLineParser implements SmartLineParser<String> {
    @Override
    public abstract void parser(String line);
}
