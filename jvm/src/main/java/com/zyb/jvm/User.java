package com.zyb.jvm;

/**
 * @author :Z1084
 * @description :
 * @create :2021-04-11 20:48:58
 */
public class User {
    static {
        System.out.println("load user");
    }

    public User() {
        System.out.println("init user");
    }
}
