package com.zyb.jvm;

/**
 * @author :Z1084
 * @description :
 * @create :2021-04-11 20:48:58
 */
public class User {
    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static {
        System.out.println("load user");
    }

    public User() {
        System.out.println("init user");
    }

    public void sout() {
        System.out.println("哈哈");
    }
}
