package com.smart.util;

import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;

/**
 * @author: Smart
 * @date: 2018/01/23 18:04
 * @explain: Excel单元格获取值
 */
public class CellUtil {
    private static final DecimalFormat DF = new DecimalFormat("#");

    /**
     * 获取Execl单元格的值
     *
     * @param cell : 单元格
     * @return
     */
    public static String getCellValue(Cell cell) {
        return getCellValue(cell, false);
    }

    /**
     * 获取Execl单元格的值
     *
     * @param cell     : 单元格
     * @param isDouble : 是否转换小数
     * @return
     */
    public static String getCellValue(Cell cell, boolean isDouble) {
        if (cell == null) {
            return null;
        }
        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
            return cell.getStringCellValue();
        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            if (isDouble) {
                return cell.getNumericCellValue() + "";
            }
            return DF.format(cell.getNumericCellValue());
        } else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
            return null;
        } else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
            return cell.getBooleanCellValue() + "";
        }
        return null;
    }
}
