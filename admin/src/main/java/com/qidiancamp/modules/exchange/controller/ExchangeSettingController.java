package com.qidiancamp.modules.exchange.controller;

import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.common.validator.ValidatorUtils;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import com.qidiancamp.modules.exchange.service.ExchangeSettingService;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 14:38:34
 */
@RestController
@RequestMapping("exchange/setting")
public class
ExchangeSettingController {
  @Autowired private ExchangeSettingService sysExchangeService;

  /** 列表 */
  @RequestMapping("/list")
  @RequiresPermissions("exchange:setting:list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = sysExchangeService.queryPage(params);

    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info/{exchangeId}")
  @RequiresPermissions("exchange:setting:info")
  public R info(@PathVariable("exchangeId") String exchangeId) {
    SysExchangeEntity sysExchange = sysExchangeService.selectById(exchangeId);

    return R.ok().put("sysExchange", sysExchange);
  }

  /** 保存 */
  @RequestMapping("/save")
  @RequiresPermissions("exchange:setting:save")
  public R save(@RequestBody SysExchangeEntity sysExchange) {
    ValidatorUtils.validateEntity(sysExchange, AddGroup.class);
    sysExchange.setCreateTime(new Date());
    sysExchangeService.insert(sysExchange);
    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  @RequiresPermissions("exchange:setting:update")
  public R update(@RequestBody SysExchangeEntity sysExchange) {
    ValidatorUtils.validateEntity(sysExchange, UpdateGroup.class);
    sysExchangeService.updateById(sysExchange);
    return R.ok();
  }

  /** 删除 */
  @RequestMapping("/delete")
  @RequiresPermissions("exchange:setting:delete")
  public R delete(@RequestBody String[] exchangeIds) {
    sysExchangeService.deleteBatchIds(Arrays.asList(exchangeIds));
    return R.ok();
  }
}
