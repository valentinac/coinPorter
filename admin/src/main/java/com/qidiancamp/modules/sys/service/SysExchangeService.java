package com.qidiancamp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.entity.SysExchangeEntity;
import java.util.Map;

/**
 * 交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 14:38:34
 */
public interface SysExchangeService extends IService<SysExchangeEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
