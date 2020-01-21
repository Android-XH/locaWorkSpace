/*
 * ************************************************************
 * 文件：WechatUtils.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月19日 20:03:59
 * 上次修改时间：2018年08月19日 20:03:59
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package com.example.wechathoke.hoke.xposed;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WechatUtils {
    //回复文本消息
    /**
     * weChat version 6.6.6
     * @param strContent
     * @param strChatroomId
     */
    public static void replyTextMessage(ClassLoader loader,String strContent, final String strChatroomId) {
        if (strContent == null || strChatroomId == null
                || strContent.length() == 0 || strChatroomId.length() == 0) {
            return;
        }

        //构造new里面的参数：
        // l iVar = new i(aao, str, hQ, i2, mVar.cvb().fD(talkerUserName, str));
        // C21619h hVar = new C21619h(str3, str, oU, i, oVar.efF().mo69035jf(talkerUserName, str2));
        //创建静态实例对象au.DF()，转换为com.tencent.mm.ab.o对象
        Class<?> classG = XposedHelpers.findClassIfExists("com.tencent.mm.kernel.g",loader);
        Object objectG = XposedHelpers.callStaticMethod(classG, "Eh");
        Object objectdpP = XposedHelpers.getObjectField(objectG, "dpP");
//        com.tencent.p128mm.modelmulti
        Class<?> classiVar = XposedHelpers.findClassIfExists("com.tencent.mm.modelmulti.i",loader);
        Object objectiVar = XposedHelpers.newInstance(classiVar,
                new Class[]{String.class, String.class, int.class, int.class, Object.class},
                strChatroomId, strContent, 1, 1, new HashMap<String, String>() {{
                    put(strChatroomId, strChatroomId);
                }});
        Object[] objectParamiVar = new Object[]{objectiVar, 0};

        //查找au.DF().a()方法
        Class<?> classDF = XposedHelpers.findClassIfExists("com.tencent.mm.ab.o",loader);
        Class<?> classI = XposedHelpers.findClassIfExists("com.tencent.mm.ab.l",loader);
        Method methodA = XposedHelpers.findMethodExactIfExists(classDF, "a", classI, int.class);
        //调用发消息方法
        try {
            XposedBridge.invokeOriginalMethod(methodA, objectdpP, objectParamiVar);
            XposedBridge.log("invokeOriginalMethod()执行成功");
        } catch (Exception e) {
            XposedBridge.log("调用微信消息回复方法异常");
            XposedBridge.log(e);
        }
    }
    //回复文本消息
    /**
     * weChat version 7.0.6
     * @param strContent
     * @param strChatroomId
     */
    public static void sendTextMessage(ClassLoader loader,String strContent, final String strChatroomId) {
        if (strContent == null || strChatroomId == null
                || strContent.length() == 0 || strChatroomId.length() == 0||loader==null) {
            return;
        }
        //构造new里面的参数：
        // new com.tencent.mm.modelmulti.h(str3, str, oU, i, oVar.efF().jf(talkerUserName, str2));
        Class<?> classiVar = XposedHelpers.findClassIfExists("com.tencent.mm.modelmulti.h",loader);
        XposedBridge.log("找到的类"+classiVar);
        Object objectiVar = XposedHelpers.newInstance(classiVar,
                new Class[]{String.class, String.class, int.class, int.class, Object.class},
                strChatroomId, strContent, 1, 1, new HashMap<String, String>() {{
                    put(strChatroomId, strChatroomId);
                }});
        Object[] objectParamiVar = new Object[]{objectiVar, 0};
        XposedBridge.log("创建的对象"+objectParamiVar);
        //创建静态实例对象az.aap()，转换为com.tencent.mm.aj.p对象
        Class<?> classG = XposedHelpers.findClassIfExists("com.tencent.mm.kernel.g",loader);
        Object objectG = XposedHelpers.callStaticMethod(classG, "aaY");
        Object objectdpP = XposedHelpers.getObjectField(objectG, "fxn");
        //查找au.DF().a()方法
        Class<?> oClass = XposedHelpers.findClass("com.tencent.mm.aj.p", loader);
        Class<?> lClass = XposedHelpers.findClass("com.tencent.mm.aj.m", loader);
        Method methodA = XposedHelpers.findMethodExact(oClass, "a", lClass, int.class);
        try {
            XposedBridge.invokeOriginalMethod(methodA, objectdpP, objectParamiVar);
            XposedBridge.log("自动回复执行成功");
        } catch (Exception e) {
            XposedBridge.log("调用微信消息回复方法异常");
            XposedBridge.log(e);
        }
    }
}
