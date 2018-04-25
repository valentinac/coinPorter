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
package com.qidiancamp.modules.job.controller;

import com.qidiancamp.common.annotation.SysLog;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.common.utils.R;
import com.qidiancamp.common.validator.ValidatorUtils;
import com.qidiancamp.modules.job.entity.ScheduleJobEntity;
import com.qidiancamp.modules.job.service.ScheduleJobService;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
  @Autowired private ScheduleJobService scheduleJobService;

  /** 定时任务列表 */
  @RequestMapping("/list")
  @RequiresPermissions("sys:schedule:list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = scheduleJobService.queryPage(params);

    return R.ok().put("page", page);
  }

  /** 定时任务信息 */
  @RequestMapping("/info/{jobId}")
  @RequiresPermissions("sys:schedule:info")
  public R info(@PathVariable("jobId") Long jobId) {
    ScheduleJobEntity schedule = scheduleJobService.selectById(jobId);

    return R.ok().put("schedule", schedule);
  }

  /** 保存定时任务 */
  @SysLog("保存定时任务")
  @RequestMapping("/save")
  @RequiresPermissions("sys:schedule:save")
  public R save(@RequestBody ScheduleJobEntity scheduleJob) {
    ValidatorUtils.validateEntity(scheduleJob);

    scheduleJobService.save(scheduleJob);

    return R.ok();
  }

  /** 修改定时任务 */
  @SysLog("修改定时任务")
  @RequestMapping("/update")
  @RequiresPermissions("sys:schedule:update")
  public R update(@RequestBody ScheduleJobEntity scheduleJob) {
    ValidatorUtils.validateEntity(scheduleJob);

    scheduleJobService.update(scheduleJob);

    return R.ok();
  }

  /** 删除定时任务 */
  @SysLog("删除定时任务")
  @RequestMapping("/delete")
  @RequiresPermissions("sys:schedule:delete")
  public R delete(@RequestBody Long[] jobIds) {
    scheduleJobService.deleteBatch(jobIds);

    return R.ok();
  }

  /** 立即执行任务 */
  @SysLog("立即执行任务")
  @RequestMapping("/run")
  @RequiresPermissions("sys:schedule:run")
  public R run(@RequestBody Long[] jobIds) {
    scheduleJobService.run(jobIds);

    return R.ok();
  }

  /** 暂停定时任务 */
  @SysLog("暂停定时任务")
  @RequestMapping("/pause")
  @RequiresPermissions("sys:schedule:pause")
  public R pause(@RequestBody Long[] jobIds) {
    scheduleJobService.pause(jobIds);

    return R.ok();
  }

  /** 恢复定时任务 */
  @SysLog("恢复定时任务")
  @RequestMapping("/resume")
  @RequiresPermissions("sys:schedule:resume")
  public R resume(@RequestBody Long[] jobIds) {
    scheduleJobService.resume(jobIds);

    return R.ok();
  }
}