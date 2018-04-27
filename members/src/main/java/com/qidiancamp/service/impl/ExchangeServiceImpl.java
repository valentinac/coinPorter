package com.qidiancamp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.dao.SysExchangeDao;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import com.qidiancamp.service.ExchangeService;
import com.qidiancamp.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("exchangeService")
public class ExchangeServiceImpl extends ServiceImpl<SysExchangeDao, SysExchangeEntity>
    implements ExchangeService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysExchangeEntity> page =
        this.selectPage(
            new Query<SysExchangeEntity>(params).getPage(), new EntityWrapper<SysExchangeEntity>());
    return new PageUtils(page);
  }
}
