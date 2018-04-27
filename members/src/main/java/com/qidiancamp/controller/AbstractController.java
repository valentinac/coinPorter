package com.qidiancamp.controller;

import com.qidiancamp.modules.sys.entity.SysMemberEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pc on 2018/4/27.
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysMemberEntity getUser() {
        SysMemberEntity se = new SysMemberEntity();
        se.setMemberId(1L);
        se.setMemberName("test");
        return se;
//        return (SysMemberEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getMemberId() {
        return getUser().getMemberId();
    }
}
