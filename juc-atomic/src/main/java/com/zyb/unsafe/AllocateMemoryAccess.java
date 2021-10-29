package com.zyb.unsafe;

import com.zyb.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * @author :Z1084
 * @description :申请堆外内存来存储数据
 * @create :2021-10-29 16:37:40
 */
public class AllocateMemoryAccess {
    public static void main(String[] args) {
        Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();
        long oneHundred = 1235464613215L;
        long size = 8;
        //申请内存
        long address = unsafe.allocateMemory(size);
        System.out.println("申请内存的地址:" + address);
        //将数据写到内存中
        unsafe.putAddress(address, oneHundred);
        //获取值
        long value = unsafe.getAddress(address);
        System.out.println("value:" + value);
        //------------------

        unsafe.putLong(address, oneHundred);
        //使用这个方法也是可以获取到值
        System.out.println("使用getlong方法获取:" + unsafe.getLong(address));
        //释放堆外内存
        unsafe.freeMemory(address);
    }
}
