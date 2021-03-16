package com.zone.commons.context;

import com.zone.commons.entity.LoginUser;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/16 9:44 下午
 * @Description:
 */
public class CurrentContext {

    public static final ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    public static void setUser(LoginUser user) {
        threadLocal.set(user);
    }

    public static LoginUser getUser() {
        return threadLocal.get();
    }

    /**
     * 用完就回收掉，防止内存泄漏
     * @link https://zhuanlan.zhihu.com/p/133433717
     */
    public static void remove() {
        threadLocal.remove();
    }

}
