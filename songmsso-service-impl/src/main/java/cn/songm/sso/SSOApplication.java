package cn.songm.sso;

import cn.songm.common.service.AppBoot;

public class SSOApplication {

    public static void main(String[] args) {
        AppBoot.start("application-sso.xml", args);
    }
}
