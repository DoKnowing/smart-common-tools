package com.smart.parser.interfaces;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author: Smart
 * @date: 2018/1/18 16:07
 */
public abstract class SmartExcelLineParser implements SmartLineParser<Row> {
    @Override
    public abstract void parser(Row row);
}
