package com.qidiancamp.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.Query;

import com.qidiancamp.modules.sys.dao.SysExchangeDao;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import com.qidiancamp.modules.sys.service.SysExchangeService;


@Service("sysExchangeService")
public class SysExchangeServiceImpl extends ServiceImpl<SysExchangeDao, SysExchangeEntity> implements SysExchangeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysExchangeEntity> page = this.selectPage(
                new Query<SysExchangeEntity>(params).getPage(),
                new EntityWrapper<SysExchangeEntity>()
        );

        return new PageUtils(page);
    }

}
