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
package com.qidiancamp.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qidiancamp.common.utils.PageUtils;
import com.qidiancamp.modules.oss.dao.SysOssDao;
import com.qidiancamp.modules.oss.entity.SysOssEntity;
import com.qidiancamp.service.SysOssService;
import com.qidiancamp.utils.Query;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity>
    implements SysOssService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysOssEntity> page = this.selectPage(new Query<SysOssEntity>(params).getPage());

    return new PageUtils(page);
  }
}
