package com.qidiancamp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.qidiancamp.common.validator.group.AddGroup;
import com.qidiancamp.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 * 
 * @author abao
 * @email abao@gmail.com
 * @date 2018-04-26 14:41:27
 */
@TableName("sys_member")
public class SysMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long memberId;
	/**
	 * 会员
	 */
	@NotBlank(message="会员名称不为空", groups = {AddGroup.class, UpdateGroup.class})
	@Size(max=10,message = "会员名称最多10个字", groups = {AddGroup.class, UpdateGroup.class})
	private String memberName;
	/**
	 * 会员密码
	 */
	@NotBlank(message="密码不为空", groups = {AddGroup.class})
	@Size(max=10,message = "密码最多10个字", groups = {AddGroup.class})
	private String memberPwd;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 谷歌code
	 */
	private String goolgeCode;
	/**
	 * 状态  0：禁用   1：正常
	 */
	@NotNull(message="状态不为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：会员
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * 获取：会员
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * 设置：会员密码
	 */
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	/**
	 * 获取：会员密码
	 */
	public String getMemberPwd() {
		return memberPwd;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：谷歌code
	 */
	public void setGoolgeCode(String goolgeCode) {
		this.goolgeCode = goolgeCode;
	}
	/**
	 * 获取：谷歌code
	 */
	public String getGoolgeCode() {
		return goolgeCode;
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
