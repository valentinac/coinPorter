package com.qidiancamp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.entity.SysMemberEntity;
import java.util.Map;

/**
 * 会员
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-25 14:35:20
 */
public interface SysMemberService extends IService<SysMemberEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
