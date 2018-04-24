package com.qidiancamp.common.utils;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by pc on 2016/2/17. */
public class CsvUtil {

  public static File createCSVFile(
      List exportData, LinkedHashMap rowMapper, String outPutPath, String filename) {
    File csvFile = null;
    BufferedWriter csvFileOutputStream = null;
    try {
      csvFile = new File(outPutPath + filename + ".csv");
      File parent = csvFile.getParentFile();
      if (parent != null && !parent.exists()) {
        parent.mkdirs();
      }
      csvFile.createNewFile();

      // GB2312使正确读取分隔符","
      csvFileOutputStream =
          new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
      // 写入文件头部
      for (Iterator propertyIterator = rowMapper.entrySet().iterator();
          propertyIterator.hasNext(); ) {
        Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
        csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
        if (propertyIterator.hasNext()) {
          csvFileOutputStream.write(",");
        }
      }
      csvFileOutputStream.newLine();

      // 写入文件内容
      for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
        LinkedHashMap row = (LinkedHashMap) iterator.next();
        System.out.println(row);

        for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext(); ) {
          Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
          csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
          if (propertyIterator.hasNext()) {
            csvFileOutputStream.write(",");
          }
        }
        if (iterator.hasNext()) {
          csvFileOutputStream.newLine();
        }
      }
      csvFileOutputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        csvFileOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return csvFile;
  }

  public static void downloadCSVFile(
      String fileName, String ctxPath, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setContentType("text/html;charset=utf-8");
    request.setCharacterEncoding("UTF-8");
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;

    String downLoadPath = ctxPath + fileName;
    try {
      long fileLength = new File(downLoadPath).length();
      response.setContentType("application/x-msdownload;");
      response.setHeader(
          "Content-disposition",
          "attachment; filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
      response.setHeader("Content-Length", String.valueOf(fileLength));
      bis = new BufferedInputStream(new FileInputStream(downLoadPath));
      bos = new BufferedOutputStream(response.getOutputStream());
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, bytesRead);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bis != null) bis.close();
      if (bos != null) bos.close();
    }
  }

  public static void main(String[] args) {
    List exportData = new ArrayList<Map>();
    Map row1 = new LinkedHashMap<String, String>();
    row1.put("1", "11");
    row1.put("2", "12");
    row1.put("3", "13");
    row1.put("4", "14");
    exportData.add(row1);
    row1 = new LinkedHashMap<String, String>();
    row1.put("1", "");
    row1.put("2", "");
    row1.put("3", "测试");
    row1.put("4", "324234");
    exportData.add(row1);
    List propertyNames = new ArrayList();
    LinkedHashMap map = new LinkedHashMap();
    map.put("1", "第一列");
    map.put("2", "第二列");
    map.put("3", "第三列");
    map.put("4", "第四列");
    String classesPath = Class.class.getClass().getResource("/").getPath();
    CsvUtil.createCSVFile(exportData, map, "/", classesPath);
    System.out.println(classesPath);
  }
}
