package cn.songm.sso;

import cn.songm.common.service.AppBoot;
import cn.songm.sso.json.JsonUtilsInit;

/**
 * 单点登入应用
 * 
 * @author zhangsong
 *
 */
public class SSOApplication {

    public static void main(String[] args) {
        JsonUtilsInit.initialization();
        AppBoot.start("application-sso.xml", args);
    }
}
