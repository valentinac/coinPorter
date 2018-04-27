package com.qidiancamp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.dao.SysMbExchangeCofigDao;
import com.qidiancamp.modules.sys.entity.SysMbExchangeCofigEntity;
import com.qidiancamp.service.MbExchangeCofigService;
import com.qidiancamp.utils.Query;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("mbExchangeCofigService")
public class MbExchangeCofigServiceImpl
    extends ServiceImpl<SysMbExchangeCofigDao, SysMbExchangeCofigEntity>
    implements MbExchangeCofigService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysMbExchangeCofigEntity> page =
        this.selectPage(
            new Query<SysMbExchangeCofigEntity>(params).getPage(),
            new EntityWrapper<SysMbExchangeCofigEntity>());

    return new PageUtils(page);
  }
}
