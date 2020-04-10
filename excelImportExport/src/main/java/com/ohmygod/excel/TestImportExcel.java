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
 * Create : 2020-04-11 ���� 5:29
 */
public class TestImportExcel {
    public static void main(String[] args) throws IOException, Exception {

        //String fileName = "student.xlsx";
        String excelPath = "C:\\Users\\Administrator\\Desktop\\test\\excelImportExport\\src\\main\\resources\\student.xlsx";
        //InputStream in = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\test\\excelImportExport\\src\\main\\resources\\student.xlsx"));
        //Workbook wb = ImportExeclUtil.chooseWorkbook(fileName, in);
        //StudentStatistics studentStatistics = new StudentStatistics();

        //��ȡһ���������Ϣ
       /* StudentStatistics readDateT =
                ImportExeclUtil.readDateT(wb, studentStatistics, in, new Integer[]{4, 5}, new Integer[]{5, 5});
        System.out.println(readDateT);*/

        //��ȡ�����б����Ϣ
        StudentBaseInfo studentBaseInfo = new StudentBaseInfo();
        //�ڶ��п�ʼ�������������н�����������ȥ���У�
        //List<StudentBaseInfo> readDateListT = ImportExeclUtil.readDateListT(wb, studentBaseInfo, 2, 0);
        List<StudentBaseInfo> readDateListT = ImportExeclUtil.readDateListT(excelPath,studentBaseInfo);

        System.out.println(readDateListT);

    }


}
