package com.qidiancamp.controller;

import java.util.Arrays;
import java.util.Map;

import com.qidiancamp.service.SysMbExchangeCofigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qidiancamp.modules.sys.entity.SysMbExchangeCofigEntity;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;



/**
 * 会员交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 15:39:06
 */
@RestController
@RequestMapping("sys/sysmbexchangecofig")
public class SysMbExchangeCofigController {
    @Autowired
    private SysMbExchangeCofigService sysMbExchangeCofigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMbExchangeCofigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cofigId}")
    public R info(@PathVariable("cofigId") Long cofigId){
			SysMbExchangeCofigEntity sysMbExchangeCofig = sysMbExchangeCofigService.selectById(cofigId);

        return R.ok().put("sysMbExchangeCofig", sysMbExchangeCofig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysMbExchangeCofigEntity sysMbExchangeCofig){
			sysMbExchangeCofigService.insert(sysMbExchangeCofig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysMbExchangeCofigEntity sysMbExchangeCofig){
			sysMbExchangeCofigService.updateById(sysMbExchangeCofig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] cofigIds){
			sysMbExchangeCofigService.deleteBatchIds(Arrays.asList(cofigIds));

        return R.ok();
    }

}
