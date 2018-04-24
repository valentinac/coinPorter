package com.qidiancamp.common.utils;

import com.qidiancamp.modules.oss.cloud.OSSFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

/** Created by pc on 2018/4/23. */
public class POIPptToImg {
  private static final String PPT = ".ppt";
  private static final String PPTX = ".pptx";

  public static String pptToImg(
      InputStream inputStream, String targetDir, String filename, String suffix) {

    String str = "";
    try {
      String pptFileName = filename;
      if (PPT.equals(suffix)) {
        str = toImage2003(inputStream, targetDir, pptFileName);
      } else if (PPTX.equals(suffix)) {
        str = toImage2007(inputStream, targetDir, pptFileName);
      } else {
        System.out.println("the file is not a ppt");
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return str;
  }

  public static String toImage2007(InputStream inputStream, String targetDir, String pptFileName)
      throws Exception {
    String str = "";
    XMLSlideShow ppt = new XMLSlideShow(inputStream);
    inputStream.close();
    Dimension pgsize = ppt.getPageSize();
    System.out.println(pgsize.width + "--" + pgsize.height);
    for (int i = 0; i < ppt.getSlides().size(); i++) {
      try {
        // 防止中文乱码
        for (XSLFShape shape : ppt.getSlides().get(i).getShapes()) {
          if (shape instanceof XSLFTextShape) {
            XSLFTextShape tsh = (XSLFTextShape) shape;
            for (XSLFTextParagraph p : tsh) {
              for (XSLFTextRun r : p) {
                r.setFontFamily("宋体");
              }
            }
          }
        }
        BufferedImage img =
            new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        // clear the drawing area
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        // render
        ppt.getSlides().get(i).draw(graphics);
        // save the output
        String imageDir = targetDir + "/";
        //                String imageDir = targetDir + "/" + pptFileName + "/";
        //                FileUtils.createDir(imageDir);// create image dir
        String imagePath = imageDir + pptFileName + "-" + (i + 1) + ".png";
        FileOutputStream out = new FileOutputStream(imagePath);
        ImageIO.write(img, "png", out);
        out.close();

        File f = new File(imagePath);
        String url = upload(f, ".png");
        str += url + ",";
        if (f.exists()) {
          f.delete();
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("第" + i + "张ppt转换出错");
      }
    }
    System.out.println("success");
    return str;
  }

  public static String toImage2003(InputStream inputStream, String targetDir, String pptFileName) {
    String str = "";
    try {
      HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(inputStream));
      Dimension pgsize = ppt.getPageSize();
      for (int i = 0; i < ppt.getSlides().size(); i++) {
        // 防止中文乱码
        for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
          if (shape instanceof HSLFTextShape) {
            HSLFTextShape tsh = (HSLFTextShape) shape;
            for (HSLFTextParagraph p : tsh) {
              for (HSLFTextRun r : p) {
                r.setFontFamily("宋体");
              }
            }
          }
        }
        BufferedImage img =
            new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        // clear the drawing area
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        // render
        ppt.getSlides().get(i).draw(graphics);
        String imageDir = targetDir + "/";
        //                String imageDir = targetDir + "/" + pptFileName + "/";
        String imagePath = imageDir + pptFileName + "-" + (i + 1) + ".png";
        FileOutputStream out = new FileOutputStream(imagePath);
        ImageIO.write(img, "png", out);
        out.close();

        File f = new File(imagePath);
        String url = upload(f, ".png");
        str += url + ",";
        if (f.exists()) {
          f.delete();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ppt转换出错");
    }
    return str;
  }

  /**
   * * 功能 :调整图片大小
   *
   * @param srcImgPath 原图片路径
   * @param distImgPath 转换大小后图片路径
   * @param width 转换后图片宽度
   * @param height 转换后图片高度
   */
  public static void resizeImage(String srcImgPath, String distImgPath, int width, int height)
      throws IOException {
    File srcFile = new File(srcImgPath);
    Image srcImg = ImageIO.read(srcFile);
    BufferedImage buffImg = null;
    buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    buffImg
        .getGraphics()
        .drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
    ImageIO.write(buffImg, "JPEG", new File(distImgPath));
  }

  public static String upload(File file, String suffix) throws IOException {
    InputStream input = new FileInputStream(file);
    String url = OSSFactory.build().uploadSuffix(input, suffix);
    return url;
  }
}
