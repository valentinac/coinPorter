package com.qidiancamp.modules.sys.controller;

import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.common.validator.ValidatorUtils;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import com.qidiancamp.modules.sys.entity.SysUserEntity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qidiancamp.modules.sys.service.SysUserRoleService;
import com.qidiancamp.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-25 14:35:20
 */
@RestController
@RequestMapping("sys/sysmember")
public class SysMemberController {
  @Autowired private SysUserService sysUserService;

  /** 列表 */
  @RequestMapping("/list")
  @RequiresPermissions("sys:sysmember:list")
  public R list(@RequestParam Map<String, Object> params) {
    params.put("user_type",1);
    PageUtils page = sysUserService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info/{userId}")
  @RequiresPermissions("sys:sysmember:info")
  public R info(@PathVariable("userId") Long userId) {
    SysUserEntity user = sysUserService.selectById(userId);
    return R.ok().put("user", user);
  }

  /** 保存 */
  @RequestMapping("/save")
  @RequiresPermissions("sys:sysmember:save")
  public R save(@RequestBody SysUserEntity user) {
    user.setDeptId(1L);
    user.setUserType(1);
    ValidatorUtils.validateEntity(user, AddGroup.class);
    sysUserService.save(user);
    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  @RequiresPermissions("sys:sysmember:update")
  public R update(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, UpdateGroup.class);
    sysUserService.update(user);
    return R.ok();
  }

  /** 删除 */
  @RequestMapping("/delete")
  @RequiresPermissions("sys:sysmember:delete")
  public R delete(@RequestBody Long[] userIds) {
    sysUserService.deleteBatchIds(Arrays.asList(userIds));
    return R.ok();
  }
}
