package com.qidiancamp.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qidiancamp.common.utils.Constant;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.common.validator.ValidatorUtils;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import com.qidiancamp.modules.sys.entity.SysDeptEntity;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import com.qidiancamp.modules.sys.entity.SysMbExchangeCofigEntity;
import com.qidiancamp.modules.sys.entity.SysMemberEntity;
import com.qidiancamp.service.ExchangeService;
import com.qidiancamp.service.MbExchangeCofigService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 15:39:06
 */
@RestController
@RequestMapping("sys/mbexchangecofig")
public class MbExchangeCofigController extends AbstractController {
  @Autowired private MbExchangeCofigService sysMbExchangeCofigService;
  @Autowired private ExchangeService exchangeService;

  /** 列表 */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = sysMbExchangeCofigService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info/{cofigId}")
  public R info(@PathVariable("cofigId") Long cofigId) {
    SysMbExchangeCofigEntity sysMbExchangeCofig = sysMbExchangeCofigService.selectById(cofigId);
    return R.ok().put("sysMbExchangeCofig", sysMbExchangeCofig);
  }

  /** 保存 */
  @RequestMapping("/save")
  public R save(@RequestBody SysMbExchangeCofigEntity sysMbExchangeCofig) {
    ValidatorUtils.validateEntity(sysMbExchangeCofig, AddGroup.class);
    SysMemberEntity sysMemberEntity = getUser();
    Map map = new HashMap();
    map.put("member_id",sysMemberEntity.getMemberId());
    map.put("exchange_id",sysMbExchangeCofig.getExchangeId());
    List<SysMbExchangeCofigEntity> list = sysMbExchangeCofigService.selectByMap(map);
    if(list.size()>0){
      return R.error("该交易所已配置");
    }
    sysMbExchangeCofig.setMemberId(sysMemberEntity.getMemberId().intValue());
    sysMbExchangeCofig.setMemberName(sysMemberEntity.getMemberName());
    sysMbExchangeCofig.setCreateTime(new Date());
    sysMbExchangeCofigService.insert(sysMbExchangeCofig);

    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  public R update(@RequestBody SysMbExchangeCofigEntity sysMbExchangeCofig) {
    ValidatorUtils.validateEntity(sysMbExchangeCofig, UpdateGroup.class);
    List<SysMbExchangeCofigEntity> list = sysMbExchangeCofigService.selectList(
            new EntityWrapper<SysMbExchangeCofigEntity>().ne("cofig_id", sysMbExchangeCofig.getCofigId())
            .eq("member_id",sysMbExchangeCofig.getMemberId()).eq("exchange_id",sysMbExchangeCofig.getExchangeId()));
    if(list.size()>0){
      return R.error("该交易所已配置");
    }
    sysMbExchangeCofigService.updateById(sysMbExchangeCofig);
    return R.ok();
  }

  /** 删除 */
  @RequestMapping("/delete")
  public R delete(@RequestBody Long[] cofigIds) {
    sysMbExchangeCofigService.deleteBatchIds(Arrays.asList(cofigIds));
    return R.ok();
  }

  /***
   * 获取交易所
   * @return
   */
  @RequestMapping("/selectexchange")
  public R select(){
    Map map = new HashMap();
    map.put("status",1);
    List<SysExchangeEntity> list = exchangeService.selectByMap(map);
    return R.ok().put("list",list);
  }
}
