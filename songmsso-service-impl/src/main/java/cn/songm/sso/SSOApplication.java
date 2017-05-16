package cn.songm.sso;

import cn.songm.common.service.AppBoot;

/**
 * 单点登入应用
 * 
 * @author zhangsong
 *
 */
public class SSOApplication {

    public static void main(String[] args) {
        AppBoot.start("application-sso.xml", args);
    }
}
