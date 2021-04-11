package com.zyb.jvm;

/**
 * @author :Z1084
 * @description :
 * 1.加载顺序：
 * 1.1 当静态变量的位置在静态代码块之前，那么加载顺序为静态变量>静态代码块>main方法>无参构造。
 * 1.2 当静态代码快的位置在静态变量之前，那么加载顺序为静态代码块>静态变量>main方法>无参构造。
 * 因为在jvm加载器中加载类时，有几个阶段，加载->验证->准备->解析->初始化->使用->卸载的过程。
 * 加载：在硬盘上查找并通过io读取字节码文件，使用类加载器进行加载，例如调用类的main()方法，new 对象等，
 * 在加载阶段会在内存中产生一个这样的对象，作为方法区中访问这个类的各种数据的访问入口。
 * <p>
 * 验证：校验字节码文件的正确性
 * <p>
 * 准备：给类的静态变量分配内存并赋予初始值
 * <p>
 * 解析：将符号引用替换成直接引用，该阶段会把一些静态方法（符号引号，比如main()方法）替换为指向数据所存内容的指针或句柄等（直接引用），
 * 这是所谓的静态链接过程（类加载期间完成），动态链接是在运行时期完成的将符号引用转换为直接引用。
 * <p>
 * 初始化：对类的静态变量初始化为指定的值，执行静态代码块。
 * @create :2021-04-11 20:43:47
 */

public class Math {
    public static User user = new User();
    public static int initData = 222;

    static {
        System.out.println("load Math");
    }

    public Math() {
        System.out.println("init Math");
    }

    public static void main(String[] args) {
        System.out.println("run main");
        Math math = new Math();
        math.compute();

    }

    public Integer compute() {
        int a = 1;
        int b = 2;
        return (a + b) * 10;
    }
}
