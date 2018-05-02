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
import com.qidiancamp.modules.sys.service.SysUserService;
import com.qidiancamp.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
public class MemberController extends AbstractController {
  @Autowired private SysUserService sysUserService;

  /** 列表 */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    params.put("userId", getUser().getUserId());
    PageUtils page = sysUserService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info")
  public R info() {
    SysUserEntity user = sysUserService.selectById(getUser().getUserId());
    return R.ok().put("user", user);
  }

  /** 保存 */
  @RequestMapping("/save")
  public R save(@RequestBody SysUserEntity user) {
    user.setDeptId(1L);
    user.setUserType(1);
    ValidatorUtils.validateEntity(user, AddGroup.class);
    sysUserService.save(user);
    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  public R update(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, UpdateGroup.class);
    sysUserService.update(user);
    return R.ok();
  }

  /** 修改登录用户密码 */
  @RequestMapping("/password")
  public R password(String password, String newPassword) {
    if(StringUtils.isEmpty(password)||StringUtils.isEmpty(newPassword)){
      return R.error("密码不为空");
    }
    // 原密码
    password = ShiroUtils.sha256(password, getUser().getSalt());
    // 新密码
    newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
    // 更新密码
    boolean flag = sysUserService.updatePassword(getUser().getUserId(), password, newPassword);
    if (!flag) {
      return R.error("原密码不正确");
    }
    return R.ok();
  }
}
