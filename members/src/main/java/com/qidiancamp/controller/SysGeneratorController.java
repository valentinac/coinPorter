package com.qidiancamp.controller;

import com.qidiancamp.utils.PageUtils;
import com.qidiancamp.utils.Query;
import com.qidiancamp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		return R.ok().put("page","");
	}
}
