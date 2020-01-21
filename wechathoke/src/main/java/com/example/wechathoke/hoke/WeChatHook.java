package com.example.wechathoke.hoke;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.wechathoke.hoke.xposed.WechatUtils;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class WeChatHook implements IXposedHookLoadPackage{
    private final String WECHAT_HOOK="com.example.wechathoke";
    private final String WECHAT_PROCESS_NAME="com.tencent.mm";
    private final String WECHAT_DATABASE_PACKAGE_NAME="com.tencent.wcdb.database.SQLiteDatabase";
    private final String INSERT_WITH_ON_CONFLICT="insertWithOnConflict";
    private Map<String,XC_LoadPackage.LoadPackageParam>mapParam;
    public  XC_LoadPackage.LoadPackageParam getLoadPackageParam(){
        return mapParam.get(WECHAT_PROCESS_NAME);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(mapParam==null){
            mapParam=new HashMap<>();
        }
        if (lpparam.processName.startsWith(WECHAT_PROCESS_NAME)) {
            mapParam.put(WECHAT_PROCESS_NAME,lpparam);
            XposedBridge.log("进入微信进程：" + lpparam.processName);
            //iMsgListener(lpparam);
            //hookSendMessage(lpparam);
            //调用 hook数据库插入。
            hookDatabaseInsert(lpparam);
        }else if(lpparam.processName.startsWith(WECHAT_HOOK)){
            XposedBridge.log("进程：" + lpparam.processName);
            if(getLoadPackageParam()!=null){
                WechatUtils.sendTextMessage(getLoadPackageParam().classLoader,"发送消息","wxid_6541755414514");
            }else{
                XposedBridge.log("getLoadPackageParam()==null");
            }
        }else{
            XposedBridge.log("其他进程：" + lpparam.processName);
        }
    }
    //hook数据库插入操作
    private void hookDatabaseInsert(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedBridge.log("进入微信进程");
        Class<?> classDb = XposedHelpers.findClassIfExists(WECHAT_DATABASE_PACKAGE_NAME,loadPackageParam.classLoader);
        if (classDb == null) {
            XposedBridge.log("hook数据库insert操作：未找到类" + WECHAT_DATABASE_PACKAGE_NAME);
            return;
        }
        XposedHelpers.findAndHookMethod(classDb,
                INSERT_WITH_ON_CONFLICT,
                String.class, String.class, ContentValues.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String tableName = (String) param.args[0];
                        ContentValues contentValues = (ContentValues) param.args[2];
                        if (tableName == null || tableName.length() == 0 || contentValues == null) {
                            return;
                        }
                        //过滤掉非聊天消息
                        if (!tableName.equals("message")){
                            return;
                        }
                        //1：表示是自己发送的消息
                        int isSend = contentValues.getAsInteger("isSend");
                        //消息内容
                        String strContent = contentValues.getAsString("content");
                        //说话人ID
                        String strTalker = contentValues.getAsString("talker");
                        //收到消息，进行回复（要判断不是自己发送的、不是群消息、不是公众号消息，才回复）
                        if (isSend != 1 && !strTalker.endsWith("@chatroom") && !strTalker.startsWith("gh_")) {
                            printInsertLog(tableName, (String) param.args[1], contentValues, (Integer) param.args[3]);
                            WechatUtils.sendTextMessage(loadPackageParam.classLoader,"回复：" + strContent+"(自动回复)", strTalker);
                        }
                    }
                });
    }

    /**
     * 注册接收消息的监听，处理UI触发流程
     */
    public void uiMsgListener(final XC_LoadPackage.LoadPackageParam lpparam) {
        XposedBridge.log("uiMsgListener 开始");
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Cursor.class;
        arrayOfObject[1] = new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws XmlPullParserException, IOException {
                //0代表别人发的消息，1代表是自己发的消息
                int field_isSend = ((Integer) XposedHelpers.getObjectField(methodHookParam.thisObject, "field_isSend"));
                //消息类型：1是文本...参考wechat_manager里的消息类型定义
                int field_type = ((Integer) XposedHelpers.getObjectField(methodHookParam.thisObject, "field_type"));
                //微信服务器端的消息id
                Object field_msgSvrId =  XposedHelpers.getObjectField(methodHookParam.thisObject, "field_msgSvrId");
                //消息内容
                String field_content = (String) XposedHelpers.getObjectField(methodHookParam.thisObject, "field_content");
                String field_talker = (String) XposedHelpers.getObjectField(methodHookParam.thisObject, "field_talker");
                //消息创建时间
                long field_createTime = (Long) XposedHelpers.getObjectField(methodHookParam.thisObject, "field_createTime");
                XposedBridge.log("uiMsgListener field_isSend:" + field_isSend + "--field_type:" + field_type + "--field_msgSvrId--" + field_msgSvrId + "--field_talker--" + field_talker + "--field_content--" + field_content);
                if (field_isSend != 1 && !field_talker.endsWith("@chatroom") && !field_talker.startsWith("gh_")) {
                    WechatUtils.sendTextMessage(lpparam.classLoader,"回复：" + field_content+"(自动回复)", field_talker);
                }
            }
        };
        XposedHelpers.findAndHookMethod("com.tencent.mm.storage.bi", lpparam.classLoader, "d", arrayOfObject);
        XposedBridge.log("uiMsgListener 结束");
    }
    //输出插入操作日志
    private void printInsertLog(String tableName, String nullColumnHack, ContentValues contentValues, int conflictValue) {
        String[] arrayConflicValues ={"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
        if (conflictValue < 0 || conflictValue > 5) {
            return;
        }
        XposedBridge.log("Hook数据库insert.table：" + tableName
                + "；nullColumnHack：" + nullColumnHack
                + "；CONFLICT_VALUES：" + arrayConflicValues[conflictValue]
                + "；contentValues:" + contentValues);
    }

}
