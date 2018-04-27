package com.qidiancamp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 交易所配置
 *
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 14:38:34
 */
@TableName("sys_exchange")
public class SysExchangeEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 交易所id */
  @TableId private String exchangeId;
  /** 交易所名称 */
  @NotBlank(
    message = "名称不为空",
    groups = {AddGroup.class, UpdateGroup.class}
  )
  @Size(
    max = 10,
    message = "名称最多10个字",
    groups = {AddGroup.class, UpdateGroup.class}
  )
  private String exchangeName;
  /** url */
  private String exchangeUrl;
  /** 状态 0：禁用 1：正常 */
  @NotNull(
    message = "状态不为空",
    groups = {AddGroup.class, UpdateGroup.class}
  )
  private Integer status;
  /** 创建时间 */
  private Date createTime;

  /** 设置：交易所id */
  public void setExchangeId(String exchangeId) {
    this.exchangeId = exchangeId;
  }
  /** 获取：交易所id */
  public String getExchangeId() {
    return exchangeId;
  }
  /** 设置：交易所名称 */
  public void setExchangeName(String exchangeName) {
    this.exchangeName = exchangeName;
  }
  /** 获取：交易所名称 */
  public String getExchangeName() {
    return exchangeName;
  }
  /** 设置：url */
  public void setExchangeUrl(String exchangeUrl) {
    this.exchangeUrl = exchangeUrl;
  }
  /** 获取：url */
  public String getExchangeUrl() {
    return exchangeUrl;
  }
  /** 设置：状态 0：禁用 1：正常 */
  public void setStatus(Integer status) {
    this.status = status;
  }
  /** 获取：状态 0：禁用 1：正常 */
  public Integer getStatus() {
    return status;
  }
  /** 设置：创建时间 */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  /** 获取：创建时间 */
  public Date getCreateTime() {
    return createTime;
  }
}