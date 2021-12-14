package com.yfcod.management.util;

import com.yfcod.management.constant.TableColumnName;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExportExcelUtil {
    public static <T> void exportExcel(Class<T> tClass, ObservableList<T> ts, String excelPath) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet();
        HSSFRow titleRow = sheet.createRow(0);

        Field[] fields = tClass.getDeclaredFields();
        Method[] methods = tClass.getMethods();

        List<Method> getterMethods = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            String[] fieldStrArr = fields[i].toString().split("\\.");
            String fieldStr = fieldStrArr[fieldStrArr.length - 1];
            String fieldStrCopy = fieldStr;

            if (fieldStr.equals("password")) continue;

            if (fieldStr.equals("address")) {
                fieldStrCopy = (tClass.toString().split("\\.")[
                        tClass.toString().split("\\.").length - 1]).toLowerCase(Locale.ROOT) + "Address";
            }

            titleRow.createCell(i).setCellValue(TableColumnName.tableColumnNames.get(fieldStrCopy));

            for (Method method : methods) {
                if (method.toString().contains("get") &&
                        method.toString().contains(
                                fieldStr.substring(0, 1).toUpperCase() + fieldStr.substring(1)
                        )) {
                    getterMethods.add(method);
                    break;
                }
            }
        }

        for (T t : ts) {
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            for (int j = 0; j < getterMethods.size(); j++) {
                try {
                    dataRow.createCell(j).setCellValue(String.valueOf(getterMethods.get(j).invoke(t)));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            hssfWorkbook.write(new FileOutputStream(excelPath));
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
