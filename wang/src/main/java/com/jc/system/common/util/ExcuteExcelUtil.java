package com.jc.system.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jc.foundation.domain.BaseBean;

/**
 * @title Excel导入导出
 * @description 业务共通类
 * @version 2014-06-28 13:00
 */
public class ExcuteExcelUtil {

    //输入流
    private static FileInputStream fileIn = null;
    //输出流
    private static OutputStream fileOut = null;
    //workbook对象
    private static Workbook wb = null;
    //模板位置
    private static String templatePath = "C://template.xls";
    //日期输入格式
    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    
    private static boolean headerStyleVisible = false;
    private static boolean bodyStyleVisible = false;

    // header默认式样
    private static short headerCellAlignment = HSSFCellStyle.ALIGN_CENTER;
    private static short headerCellVerticalAlignment = HSSFCellStyle.ALIGN_CENTER;
    private static short headerCellFillPattern = HSSFCellStyle.SOLID_FOREGROUND;
    private static short headerCellFillForegroundColor = HSSFColor.SEA_GREEN.index;
    private static short headerCellBorder = HSSFCellStyle.BORDER_THIN;
    private static short headerFontColor = HSSFColor.SKY_BLUE.index;
    private static short headerFontHeight = HSSFFont.BOLDWEIGHT_BOLD;
    private static short headerFontSize = 12;

    // body默认式样
    private static short bodyCellAlignment = HSSFCellStyle.ALIGN_CENTER;
    private static short bodyCellVerticalAlignment = HSSFCellStyle.ALIGN_CENTER;
    private static short bodyCellFillPattern = HSSFCellStyle.SOLID_FOREGROUND;
    private static short bodyCellFillForegroundColor = HSSFColor.SEA_GREEN.index;
    private static short bodyCellBorder = HSSFCellStyle.BORDER_THIN;
    private static short bodyFontColor = HSSFColor.BLACK.index;
    private static short bodyFontHeight = HSSFFont.BOLDWEIGHT_BOLD;
    private static short bodyFontSize = 12;

    /**
     * 创建HSSFWorkbook对象
     */
    public static void createExcel2003() {

        wb = new HSSFWorkbook();
    }

    /**
     * 创建XSSFWorkbook对象
     */
    public static void createExcel2007() {

        wb = new XSSFWorkbook();

    }
    
    /**
     * 创建HSSFWorkbook对象
     * @throws IOException 
     */
    public static void createExcel2003(String path) throws IOException {
        fileIn = new FileInputStream(path);
        wb = new HSSFWorkbook(new POIFSFileSystem(fileIn));
        fileIn.close();
    }

    /**
     * 创建XSSFWorkbook对象
     * @throws IOException 
     */
    public static void createExcel2007(String path) throws IOException {
        fileIn = new FileInputStream(path);
        wb = new XSSFWorkbook(fileIn);
        fileIn.close();
    }
    
    /**
     * 根据模板创建2003Excel对象
     * @throws IOException 
     */
    public static void createExcel2003ByTemplate(String path) throws IOException {
        createExcel2003(path);
    }
    
    /**
     * 根据模板创建2007Excel对象
     * @throws IOException 
     */
    public static void createExcel2007ByTemplate(File file) throws IOException {
        
        fileIn = new FileInputStream(file);
        wb = new XSSFWorkbook(fileIn);
    }

    /**
     * 创建标题行外观
     */
    public static CellStyle getHeaderStyle() {

        CellStyle cs = wb.createCellStyle();
        
        if(headerStyleVisible){
            cs.setAlignment(headerCellAlignment);
            cs.setVerticalAlignment(headerCellVerticalAlignment);
            cs.setFillForegroundColor(headerCellFillForegroundColor);
            cs.setFillPattern(headerCellFillPattern);

            cs.setBorderBottom(headerCellBorder);
            cs.setBorderLeft(headerCellBorder);
            cs.setBorderRight(headerCellBorder);
            cs.setBorderTop(headerCellBorder);

            Font ff = wb.createFont();
            ff.setColor(headerFontColor);
            ff.setFontHeight(headerFontHeight);
            ff.setFontHeightInPoints(headerFontSize);

            cs.setFont(ff);
        }
        
        return cs;
    }

    /**
     * 创建内容行外观
     */
    public static CellStyle getBodyStyle() {
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        if(bodyStyleVisible){
            cs.setAlignment(bodyCellAlignment);
            cs.setVerticalAlignment(bodyCellVerticalAlignment);
            cs.setFillForegroundColor(bodyCellFillForegroundColor);
            cs.setFillPattern(bodyCellFillPattern);

            cs.setBorderBottom(bodyCellBorder);
            cs.setBorderLeft(bodyCellBorder);
            cs.setBorderRight(bodyCellBorder);
            cs.setBorderTop(bodyCellBorder);

            Font ff = wb.createFont();
            ff.setColor(bodyFontColor);
            ff.setFontHeight(bodyFontHeight);
            ff.setFontHeightInPoints(bodyFontSize);

            cs.setFont(ff);
        }
        return cs;
    }
    
    /**
     * 设置模板路径
     * @param path 模板路径
     */
    public static void setTemplatePath(String path){
        templatePath = path;
    }
    
    /**
     * 设置日期格式
     * @param format yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd
     */
    public static void setDateFormat(String format){
    	dateFormat = format;
    }
    
    /**
     * 设置response参数
     * @param response response对象
     */
    public static void setResponse(HttpServletResponse response){
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("content-disposition", "attachment;filename = excel.xls");
    }

    /**
     * 设置标题
     * @param lstHead 列名list
     * @param sheet ExcelSheet对象
     */
    private static void exportHeader(List<String> lstHead, Sheet sheet) {
        
        // 创建行:标题为第一行
        Row rowHead = sheet.createRow(0);
        
        for (int i = 0; i < lstHead.size(); i++) {
            
            // 创建列
            Cell cell = rowHead.createCell(i);

            // 设置外观
            cell.setCellStyle(getHeaderStyle());

            cell.setCellValue(lstHead.get(i));
        }
    }

    /**
     * 根据标题导出excel
     * @param lstHead 列名
     * @param lstProp 需要导出的属性list
     * @param lstObj 需要导出的实体beanlist
     * @param response response
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws NoSuchFieldException 
     */
    public static void exportExcel(List<String> lstHead, List<String> lstProp, 
            List<? extends BaseBean> lstObj, HttpServletResponse response) throws IOException, NoSuchFieldException {

        //目前只支持Excel2003
        createExcel2003();

        try {

            //默认sheet名为sheet1,数据大于6W条时,追加多页处理
            Sheet sheet = wb.createSheet("sheet1");

            // 导出标题
            exportHeader(lstHead, sheet);

            // 导出数据
            exportBody(lstObj, lstProp, sheet);
            
            //设置response
            setResponse(response);
           
            fileOut = response.getOutputStream();
            
            // 写入Excel
            wb.write(fileOut);
            
            fileOut.flush();
            
            // 关闭输出流
            fileOut.close();
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            ex.printStackTrace();
        } finally {
            if (fileOut != null) {
                // 关闭输出流
                fileOut.close();
            }
        }
    }
    
    /**
     * 根据模板导出excel
     * @param lstObj 需要导出的实体beanlist
     * @param lstProp 需要导出的属性list
     * @param response response
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws NoSuchFieldException 
     */
    public static void exportExcel(List<? extends BaseBean> lstObj, List<String> lstProp, HttpServletResponse response) throws IOException, NoSuchFieldException {

        //目前只支持Excel2003
        createExcel2003ByTemplate(templatePath);

        try {
            
            //默认sheet名为sheet1,数据大于6W条时,追加多页处理
            Sheet sheet = wb.getSheetAt(0);

            // 导出数据
            exportBody(lstObj, lstProp, sheet);
            
            //设置response
            setResponse(response);
            
            fileOut = response.getOutputStream();

            // 写入Excel
            wb.write(fileOut);

            // 关闭输出流
            fileOut.close();
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            ex.printStackTrace();
        } finally {
            if (fileOut != null) {
                // 关闭输出流
                fileOut.close();
            }
        }

    }

    /**
     * 导出数据
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
//    private static void exportBody(List<? extends BaseBean> lstObj, Sheet sheet)
//            throws NoSuchMethodException, SecurityException,
//            IllegalAccessException, IllegalArgumentException,
//            InvocationTargetException {
//
//        for (int i = 0; i < lstObj.size(); i++) {
//
//            BaseBean bBean = lstObj.get(i);
//
//            // 创建行
//            Row rowBody = sheet.createRow(i + 1);
//
//            // 获取实体类的所有属性，返回Field数组
//            Field[] field = bBean.getClass().getDeclaredFields();
//
//            // 开始写入列的位置
//            int nCellStart = 0;
//
//            // 遍历所有属性
//            for (int j = 0; j < field.length; j++) {
//
//                // 自适应列宽度
//                // sheet.autoSizeColumn((short)j);
//
//                // 获取属性的名字
//                String name = field[j].getName();
//
//                // 获得属性的类型
//                String type = field[j].getGenericType().toString();
//
//                if ("serialVersionUID".equals(name)) {
//                    continue;
//                }
//
//                // 将属性的首字符大写，构造get方法
//                name = name.substring(0, 1).toUpperCase() + name.substring(1);
//
//                // 调用get方法
//                Method getMethod = bBean.getClass().getMethod("get" + name);
//
//                // 创建列
//                Cell cell = rowBody.createCell(nCellStart);
//
//                // 设置外观
//                cell.setCellStyle(getBodyStyle());
//
//                //日期时特殊处理
//                if(type.equals("class java.util.Date")){
//                    SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    cell.setCellValue(getMethod.invoke(bBean) != null ? sFormat.format((Date)getMethod.invoke(bBean)) : "");
//                }
//                else{
//                    cell.setCellValue(getMethod.invoke(bBean) != null ? getMethod.invoke(bBean) + "": "");
//                }
//
//                nCellStart++;
//            }
//
//        }
//    }
    
    /**
     * 导出数据
     * @param lstObj 需要导出的实体beanlist
     * @param lstProp 需要导出的属性list
     * @param sheet Sheet 对象
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException 
     */
    private static void exportBody(List<? extends BaseBean> lstObj, List<String> lstProp, Sheet sheet)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException {

        for (int i = 0; i < lstObj.size(); i++) {

            BaseBean bBean = lstObj.get(i);

            // 创建行
            Row rowBody = sheet.createRow(i + 1);

            // 获取实体类的所有属性，返回Field数组
            //Field[] field = bBean.getClass().getDeclaredFields();

            // 开始写入列的位置
            int nCellStart = 0;

            // 遍历所有属性
            for (int j = 0; j < lstProp.size(); j++) {

                // 自适应列宽度
                // sheet.autoSizeColumn((short)j);

                // 获取属性的名字
                String name = lstProp.get(j);

                Field field = bBean.getClass().getDeclaredField(name);
                
                // 获得属性的类型
                String type = field.getGenericType().toString();

                //设备导出excel用，由于实体类中get方法首字母是小写的，
                if(!name.equals("uCount")&&!name.equals("aCurrent")&&!name.equals("bCurrent")){
                	// 将属性的首字符大写，构造get方法
                	name = name.substring(0, 1).toUpperCase() + name.substring(1);
                }
                
                // 调用get方法
                Method getMethod = bBean.getClass().getMethod("get" + name);

                // 创建列
                Cell cell = rowBody.createCell(nCellStart);

                // 设置外观
                //cell.setCellStyle(getBodyStyle());

                //日期时特殊处理
                if(type.equals("class java.util.Date")){
                    SimpleDateFormat sFormat = new SimpleDateFormat(dateFormat);
                    cell.setCellValue(getMethod.invoke(bBean) != null ? sFormat.format((Date)getMethod.invoke(bBean)) : "");
                }
                else{
                	String value = "";
                	Object objValue = getMethod.invoke(bBean);
                	if(objValue != null){
                		value = objValue +"";
                	}
                	
                    cell.setCellValue(getMethod.invoke(bBean) != null ? getMethod.invoke(bBean) + "": "");
                }

                nCellStart++;
            }

        }
    }

    /**
     * 导入Excel
     * @param inputStream 文件输入流
     * @param bBean BaseBean
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws ParseException 
     * @throws IOException 
     * @throws InstantiationException 
     */
    public static List<BaseBean> importExcel(InputStream inputStream, BaseBean bBean)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ParseException, IOException, InstantiationException {
        List<BaseBean> bbList = new ArrayList<BaseBean>();
        try {
            Workbook wb = new HSSFWorkbook(inputStream);
            
            // 获取实体类的所有属性，返回Field数组
            Field[] field = bBean.getClass().getDeclaredFields();

            // 读取页数
//            int nSheetNum = wb.getNumberOfSheets();
            
            //只有一个sheet
            Sheet sheet = wb.getSheetAt(0);

            // 读取行数(取得的是行标,比行数小1.)
            int nRowCount = sheet.getLastRowNum();
            for (int i = 1; i <= nRowCount; i++) {
                bBean = bBean.getClass().newInstance();
                Row row = sheet.getRow(i);

                // 读取列数(取得的是列标,比列数大1.)
                int nCellCount = row.getLastCellNum();
                
                // 开始列的位置
                int nCellStart = 0;
                for (int j = 0; j < nCellCount; j++) {
                    Cell cell = row.getCell(nCellStart);
                    
                    // 获取属性的名字
                    String name = field[j].getName();

                    // 获得属性的类型
                    String type = field[j].getGenericType().toString();

                    if ("serialVersionUID".equals(name)) {
                        continue;
                    }

                    // 将属性的首字符大写，构造get方法
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);

                    // 调用set方法
                    Method setMethod = null;
                    if(type.equals("class java.lang.String")){
                        setMethod = bBean.getClass().getMethod("set" + name, String.class);
                        setMethod.invoke(bBean, cell.getStringCellValue());
         
                    }else if(type.equals("class java.lang.Integer")){
                        setMethod = bBean.getClass().getMethod("set" + name, Integer.class);
                        
                        if(cell.getStringCellValue().equals("")){
                            setMethod.invoke(bBean, (Integer)null);
                        }else{
                            setMethod.invoke(bBean, Integer.parseInt(cell.getStringCellValue()));
                        }
                        
                    }else if(type.equals("class java.lang.Double")){
                        setMethod = bBean.getClass().getMethod("set" + name, Integer.class);
                        
                        if(cell.getStringCellValue().equals("")){
                            setMethod.invoke(bBean, (Double)null);
                        }else{
                            setMethod.invoke(bBean, Double.parseDouble(cell.getStringCellValue()));
                        }
                        
                    }else if(type.equals("class java.util.Date")){
                        setMethod = bBean.getClass().getMethod("set" + name, Date.class);

                        //格式化日期
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        Date date = null;
                        
                        if(!"".equals(cell.getStringCellValue())){
                            date = sdf.parse(cell.getStringCellValue());
                        }
                        setMethod.invoke(bBean, date);
                        
                    }else if(type.equals("class java.lang.Boolean")){
                        setMethod = bBean.getClass().getMethod("set" + name, Boolean.class);
                        setMethod.invoke(bBean, cell.getBooleanCellValue());
                    }
                    nCellStart++;
                }
                bbList.add(bBean);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(inputStream != null){
                inputStream.close();
            }
        }
        return bbList;
    }
}