package com.zyb;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author：Z1084
 * @description：
 * @create：2023-06-08 15:06
 */
public class JavassistDemo {
    public static void main(String[] args) throws Exception {
        ClassPool.getDefault().insertClassPath("E:\\ideaWorkspace\\java-advanced\\java-javassist\\src\\main\\resources\\spring-boot-starter-mqtt-1.1.0.jar");
        ClassPool.getDefault().insertClassPath("E:\\ideaWorkspace\\java-advanced\\java-javassist\\src\\main\\resources\\truelicense-core-1.33.jar");

        CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.zyb.license.CustomLicenseManager");
        for (CtMethod declaredMethod : zzZJJClass.getDeclaredMethods()) {
            if ("validate".equals(declaredMethod.getName())) {
                declaredMethod.setBody("{return;}");
                //declaredMethod.setBody("{ GenericCertificate certificate = this.getPrivacyGuard().key2cert(key);\n" +
                //        "        notary.verify(certificate);\n" +
                //        "        LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());\n" +
                //        "        this.setLicenseKey(key);\n" +
                //        "        this.setCertificate(certificate);\n" +
                //        "        return content;}");
                zzZJJClass.writeFile("E:\\ideaWorkspace\\java-advanced\\java-javassist\\src\\main\\resources");
                return;
            }
        }
    }
}
