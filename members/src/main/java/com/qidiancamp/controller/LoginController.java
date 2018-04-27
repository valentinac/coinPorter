package com.qidiancamp.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 */
@Controller
public class LoginController {
  @Autowired
  private Producer producer;
  @RequestMapping("captcha.jpg")
  public void captcha(HttpServletResponse response,HttpServletRequest request) throws IOException {
    response.setHeader("Cache-Control", "no-store, no-cache");
    response.setContentType("image/jpeg");
    // 生成文字验证码
    String text = producer.createText();
    // 生成图片验证码
    BufferedImage image = producer.createImage(text);
    // 保存到shiro session
    request.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(image, "jpg", out);
  }

  /** 登录 */
  @ResponseBody
  @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
  public R login(String username, String password, String captcha) {
    return R.ok();
  }

  /** 退出 */
  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public String logout() {
    return "redirect:login.html";
  }
}
