package com.qidiancamp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.sys.dao.SysMemberDao;
import com.qidiancamp.modules.sys.entity.SysMemberEntity;
import com.qidiancamp.service.MemberService;
import com.qidiancamp.utils.Query;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<SysMemberDao, SysMemberEntity>
    implements MemberService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysMemberEntity> page =
        this.selectPage(
            new Query<SysMemberEntity>(params).getPage(), new EntityWrapper<SysMemberEntity>());

    return new PageUtils(page);
  }
}
