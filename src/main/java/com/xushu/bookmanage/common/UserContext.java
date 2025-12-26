package com.xushu.bookmanage.common;

import java.util.Map;

public class UserContext {
    private static final ThreadLocal<Map<String, Object>> userThreadLocal = new ThreadLocal<>();

    public static void setUser(Map<String, Object> user) {
        userThreadLocal.set(user);
    }

    public static Map<String, Object> getUser() {
        return userThreadLocal.get();
    }

    public static Long getCurrentUserId() {
        Map<String, Object> user = getUser();
        if (user != null && user.get("id") != null) {
            return Long.valueOf(user.get("id").toString());
        }
        return null;
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
