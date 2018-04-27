package com.qidiancamp.service;

import com.baomidou.mybatisplus.service.IService;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.entity.SysMbExchangeCofigEntity;
import java.util.Map;

/**
 * 会员交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 15:39:06
 */
public interface MbExchangeCofigService extends IService<SysMbExchangeCofigEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
