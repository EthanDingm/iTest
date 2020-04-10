package com.ohmygod.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Author : Dingm
 * Description :
 * Create : 2020-04-11 上午 5:29
 */
public class TestImportExcel {
    public static void main(String[] args) throws IOException, Exception {

        //String fileName = "student.xlsx";
        String excelPath = "C:\\Users\\Administrator\\Desktop\\test\\excelImportExport\\src\\main\\resources\\student.xlsx";
        //InputStream in = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\test\\excelImportExport\\src\\main\\resources\\student.xlsx"));
        //Workbook wb = ImportExeclUtil.chooseWorkbook(fileName, in);
        //StudentStatistics studentStatistics = new StudentStatistics();

        //读取一个对象的信息
       /* StudentStatistics readDateT =
                ImportExeclUtil.readDateT(wb, studentStatistics, in, new Integer[]{4, 5}, new Integer[]{5, 5});
        System.out.println(readDateT);*/

        //读取对象列表的信息
        StudentBaseInfo studentBaseInfo = new StudentBaseInfo();
        //第二行开始，到倒数第三行结束（总数减去两行）
        //List<StudentBaseInfo> readDateListT = ImportExeclUtil.readDateListT(wb, studentBaseInfo, 2, 0);
        List<StudentBaseInfo> readDateListT = ImportExeclUtil.readDateListT(excelPath,studentBaseInfo);

        System.out.println(readDateListT);

    }


}
