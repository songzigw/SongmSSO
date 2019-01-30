package cn.songm.sso.redis;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -7388042848753495936L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
