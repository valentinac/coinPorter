package com.qidiancamp.controller;

import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.common.validator.ValidatorUtils;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import com.qidiancamp.modules.sys.entity.SysMemberEntity;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.qidiancamp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-25 14:35:20
 */
@RestController
@RequestMapping("sys/member")
public class MemberController {
  @Autowired private MemberService sysMemberService;

  /** 列表 */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = sysMemberService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info/{memberId}")
  public R info(@PathVariable("memberId") Long memberId) {
    SysMemberEntity sysMember = sysMemberService.selectById(memberId);
    return R.ok().put("sysMember", sysMember);
  }

  /** 保存 */
  @RequestMapping("/save")
  public R save(@RequestBody SysMemberEntity sysMember) {
    ValidatorUtils.validateEntity(sysMember, AddGroup.class);
    sysMember.setCreateTime(new Date());
    sysMemberService.insert(sysMember);
    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  public R update(@RequestBody SysMemberEntity sysMember) {
    ValidatorUtils.validateEntity(sysMember, UpdateGroup.class);
    sysMemberService.updateById(sysMember);
    return R.ok();
  }

  /** 删除 */
  @RequestMapping("/delete")
  public R delete(@RequestBody Long[] memberIds) {
    sysMemberService.deleteBatchIds(Arrays.asList(memberIds));
    return R.ok();
  }
}
