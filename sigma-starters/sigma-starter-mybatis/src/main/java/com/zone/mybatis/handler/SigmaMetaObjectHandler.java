package com.zone.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/30 1:36 下午
 * @Description: 自动填充的功能
 */
@Slf4j
public class SigmaMetaObjectHandler implements MetaObjectHandler {


    private final static String CREATE_BY = "createBy";
    private final static String UPDATE_BY = "updateBy";
    private final static String CREATE_NAME = "createName";
    private final static String UPDATE_NAME = "updateName";
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {

        LoginUser loginUser = CurrentContext.getUser();
        if (loginUser == null) {
            return;
        }
        // 插入可能涉及create相关数据已经有值（比如对老数据的删除，然后重新插入）
        // 所以这里要判断是否有值
        Object createByVal = getFieldValByName(CREATE_BY, metaObject);
        Object createName = getFieldValByName(CREATE_NAME, metaObject);
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        if (createByVal == null) {
            this.strictInsertFill(metaObject, CREATE_BY, Long.class, loginUser.getAccountId());
        }
        if (createName == null) {
            this.strictInsertFill(metaObject, CREATE_NAME, String.class, loginUser.getAccountName());
        }
        if (createTime == null) {
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }
        this.strictInsertFill(metaObject, UPDATE_BY, Long.class, loginUser.getAccountId());
        this.strictInsertFill(metaObject, UPDATE_NAME, String.class, loginUser.getAccountName());
        this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        log.info("用户[{}]新增数据", loginUser.getAccountName() + "(" + loginUser.getAccountId() + ")");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = CurrentContext.getUser();
        if (loginUser == null) {
            return;
        }
        this.strictUpdateFill(metaObject, UPDATE_BY, Long.class, loginUser.getAccountId());
        this.strictUpdateFill(metaObject, UPDATE_NAME, String.class, loginUser.getAccountName());
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        log.info("用户[{}]更新数据", loginUser.getAccountName() + "(" + loginUser.getAccountId() + ")");
    }
}
