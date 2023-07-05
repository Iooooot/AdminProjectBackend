package com.wht.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wht.entity.LoginUser;
import com.wht.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis字段自动填充，创建时间、更新时间、创建者、更新者
 * @author wht
 * @date 2022/10/14 13:45
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间
        this.strictInsertFill(metaObject, "createTime", () -> new Date(), Date.class);
        // 填充创建者
        this.strictInsertFill(metaObject, "createBy", () -> SecurityUtils.getCurrentUser().getUsername(), String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", () -> new Date(), Date.class);
        // 填充更新者
        LoginUser currentUser = SecurityUtils.getCurrentUser();
        if(currentUser != null){
            this.strictUpdateFill(metaObject, "updateBy", () -> SecurityUtils.getCurrentUser().getUsername(), String.class);
        }
    }
}
