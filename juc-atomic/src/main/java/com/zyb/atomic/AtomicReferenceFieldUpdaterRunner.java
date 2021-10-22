package com.zyb.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author :Z1084
 * @description :使用cas去修改实体类中的某个属性值,这里的修改会将实体类里面的属性进行修改
 * @create :2021-10-22 09:54:29
 */
public class AtomicReferenceFieldUpdaterRunner {
    static AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Document.class, String.class, "name");

    public static void main(String[] args) {
        Document document = new Document("zhangsan", 1);
        System.out.println(updater.get(document));
        updater.set(document, "wangwu");
        System.out.println(updater.get(document));
        //getAndUpdate方法是先获取旧值然后再去赋值
        updater.getAndUpdate(document, (s) -> {
            System.out.println("以前的值:" + s);
            return "lisi";
        });
        System.out.println(updater.get(document));
        //先去修改在获取最新的值
        System.out.println(updater.updateAndGet(document, s -> "maliu"));
        System.out.println("实体类中的name:" + document.getName());
    }

    @AllArgsConstructor
    @Data
    static class Document {
        public volatile String name;
        private int version;
    }
}

