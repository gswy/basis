package xin.wanyun.server.thread;


import xin.wanyun.server.entity.AdminUser;

/**
 * 线程保存当前验证过的用户信息
 */
public class AdminUserThread {

    private AdminUserThread() {}

    private static final ThreadLocal<AdminUser> LOCAL = new ThreadLocal<>();

    public static void put(AdminUser adminUser) {
        LOCAL.set(adminUser);
    }

    public static AdminUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
