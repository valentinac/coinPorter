/**
 * Copyright 2018 人人开源 http://www.renren.io
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qidiancamp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.annotation.DataFilter;
import com.qidiancamp.common.utils.Constant;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.Query;
import com.qidiancamp.modules.sys.dao.SysUserDao;
import com.qidiancamp.modules.sys.entity.SysDeptEntity;
import com.qidiancamp.modules.sys.entity.SysUserEntity;
import com.qidiancamp.modules.sys.service.SysDeptService;
import com.qidiancamp.modules.sys.service.SysUserRoleService;
import com.qidiancamp.modules.sys.service.SysUserService;
import com.qidiancamp.modules.sys.shiro.ShiroUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity>
    implements SysUserService {
  @Autowired private SysUserRoleService sysUserRoleService;
  @Autowired private SysDeptService sysDeptService;

  @Override
  public List<Long> queryAllMenuId(Long userId) {
    return baseMapper.queryAllMenuId(userId);
  }

  @Override
  @DataFilter(subDept = true, user = false)
  public PageUtils queryPage(Map<String, Object> params) {
    String username = (String) params.get("username");
    Integer user_type = (Integer)params.get("user_type");
    Page<SysUserEntity> page =
        this.selectPage(
            new Query<SysUserEntity>(params).getPage(),
            new EntityWrapper<SysUserEntity>()
                .like(StringUtils.isNotBlank(username), "username", username)
                .addFilterIfNeed(
                    params.get(Constant.SQL_FILTER) != null,
                    (String) params.get(Constant.SQL_FILTER))
                .eq("user_type",user_type)
        );

    for (SysUserEntity sysUserEntity : page.getRecords()) {
      SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
      sysUserEntity.setDeptName(sysDeptEntity.getName());
    }

    return new PageUtils(page);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(SysUserEntity user) {
    user.setCreateTime(new Date());
    // sha256加密
    String salt = RandomStringUtils.randomAlphanumeric(20);
    user.setSalt(salt);
    user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
    this.insert(user);

    // 保存用户与角色关系
    sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(SysUserEntity user) {
    if (StringUtils.isBlank(user.getPassword())) {
      user.setPassword(null);
    } else {
      user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
    }
    this.updateById(user);

    // 保存用户与角色关系
    sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
  }

  @Override
  public boolean updatePassword(Long userId, String password, String newPassword) {
    SysUserEntity userEntity = new SysUserEntity();
    userEntity.setPassword(newPassword);
    return this.update(
        userEntity,
        new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
  }
}
