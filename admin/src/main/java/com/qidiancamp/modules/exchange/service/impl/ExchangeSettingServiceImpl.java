package com.qidiancamp.modules.exchange.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.Query;
import com.qidiancamp.modules.sys.dao.SysExchangeDao;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import com.qidiancamp.modules.exchange.service.ExchangeSettingService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("sysExchangeService")
public class ExchangeSettingServiceImpl extends ServiceImpl<SysExchangeDao, SysExchangeEntity>
    implements ExchangeSettingService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysExchangeEntity> page =
        this.selectPage(
            new Query<SysExchangeEntity>(params).getPage(), new EntityWrapper<SysExchangeEntity>());

    return new PageUtils(page);
  }
}
