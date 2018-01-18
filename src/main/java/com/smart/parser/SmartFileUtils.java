package com.smart.parser;


import com.smart.parser.interfaces.SmartCsvLineParser;
import com.smart.parser.interfaces.SmartExcelLineParser;
import com.smart.parser.interfaces.SmartTxtLineParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.Iterator;

/**
 * @author: Smart
 * @date: 2018/1/18 16:17
 */
public class SmartFileUtils {

    /**
     * 加载 excel文件
     *
     * @param path
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, SmartExcelLineParser parser) throws Exception {
        loadExcelFile(path, 0, false, parser);
    }

    /**
     * 处理excel文件
     *
     * @param path
     * @param sheetNumber
     * @param containHeader
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, int sheetNumber, boolean containHeader, SmartExcelLineParser parser) throws Exception {
        loadExcelFile(path, sheetNumber, containHeader ? 0 : 1, parser);
    }

    /**
     * 处理excel文件
     *
     * @param path
     * @param sheetNumber
     * @param begin
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, int sheetNumber, int begin, SmartExcelLineParser parser) throws Exception {
        Sheet sheet = SmartFileLoadUtil.loadExcel(path, sheetNumber);
        // 获取工作表的总行数
        int number = sheet.getLastRowNum();
        // 遍历每一行
        int count = begin;

        while (count <= number) {
            parser.parser(sheet.getRow(count));
            count++;
        }
        sheet = null;
    }

    /**
     * 处理csv文件
     *
     * @param path
     * @param parser
     * @throws IOException
     */
    public static void loadCsvFile(String path, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, "GBK", parser);
    }

    /**
     * 处理csv文件
     *
     * @param path
     * @param code
     * @param parser
     * @throws IOException
     */
    public static void loadCsvFile(String path, String code, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, code, CSVFormat.DEFAULT, false, parser);
    }

    public static void loadCsvFile(String path, String code, CSVFormat format, boolean containHeader, SmartCsvLineParser parser) throws IOException {
        CSVParser csvParser = SmartFileLoadUtil.loadCSV(path, code, format);
        Iterator<CSVRecord> iterator = csvParser.iterator();
        // 去掉header
        if (!containHeader && iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            parser.parser(iterator.next());
        }
        csvParser.close();
        csvParser = null;
    }

    public static void loadTxtFile(String path, SmartTxtLineParser parser) throws IOException {
        loadTxtFile(path, "UTF-8", parser);
    }

    public static void loadTxtFile(String path, String code, SmartTxtLineParser parser) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), code));
        String line = "";
        while ((line = reader.readLine()) != null) {
            parser.parser(line);
        }
        reader.close();
        reader = null;
    }


    private static class SmartFileLoadUtil {
        private static final String XLSX_SUFFIX = ".xlsx";
        private static final String XLS_SUFFIX = ".xls";

        /**
         * 加载 excel文件
         *
         * @param path
         * @param sheetNumber
         * @return
         * @throws Exception
         */
        protected static Sheet loadExcel(String path, int sheetNumber) throws Exception {
            // estimate file is excel(xlsx.xls) file
            String fileName = new File(path).getName();
            if (StringUtils.isEmpty(fileName) || !(fileName.endsWith(XLSX_SUFFIX) | fileName.endsWith(XLS_SUFFIX))) {
                throw new Exception(path + " is not excel which is must end with \".xlsx\" or \".xls\"");
            }
            return WorkbookFactory.create(new File(path)).getSheetAt(sheetNumber);
        }

        /**
         * 加载 csv文件
         *
         * @param path
         * @return
         * @throws IOException
         */
        protected static CSVParser loadCSV(String path) throws IOException {
            return loadCSV(path, "GBK", CSVFormat.DEFAULT);
        }

        /**
         * 加载 csv文件
         *
         * @param path
         * @param code
         * @return
         * @throws IOException
         */
        protected static CSVParser loadCSV(String path, String code) throws IOException {
            return loadCSV(path, code, CSVFormat.DEFAULT);
        }

        /**
         * 加载 csv文件
         *
         * @param path
         * @param code
         * @param format
         * @return
         * @throws IOException
         */
        protected static CSVParser loadCSV(String path, String code, CSVFormat format) throws IOException {
            return new CSVParser(new BufferedReader(new InputStreamReader(new FileInputStream(path), code)), format);
        }
    }
}
