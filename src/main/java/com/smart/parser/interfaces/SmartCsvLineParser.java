package com.smart.parser.interfaces;

import org.apache.commons.csv.CSVRecord;

/**
 * @author: Smart
 * @date: 2018/1/18 16:07
 */
public abstract class SmartCsvLineParser implements SmartLineParser<CSVRecord> {
    @Override
    public abstract void parser(CSVRecord csvRecord);
}
