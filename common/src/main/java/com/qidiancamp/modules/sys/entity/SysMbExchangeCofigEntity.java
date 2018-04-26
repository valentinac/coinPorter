package com.qidiancamp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员交易所配置
 * 
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 15:39:06
 */
@TableName("sys_mb_exchange_cofig")
public class SysMbExchangeCofigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long cofigId;
	/**
	 * 交易所id
	 */
	private String exchangeId;
	/**
	 * 交易所名称
	 */
	private String exchangeName;
	/**
	 * apiKey
	 */
	private String apiKey;
	/**
	 * secretKey
	 */
	private String secretKey;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setCofigId(Long cofigId) {
		this.cofigId = cofigId;
	}
	/**
	 * 获取：
	 */
	public Long getCofigId() {
		return cofigId;
	}
	/**
	 * 设置：交易所id
	 */
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	/**
	 * 获取：交易所id
	 */
	public String getExchangeId() {
		return exchangeId;
	}
	/**
	 * 设置：交易所名称
	 */
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	/**
	 * 获取：交易所名称
	 */
	public String getExchangeName() {
		return exchangeName;
	}
	/**
	 * 设置：apiKey
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	/**
	 * 获取：apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * 设置：secretKey
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	/**
	 * 获取：secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}
	/**
	 * 设置：状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0：禁用   1：正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
