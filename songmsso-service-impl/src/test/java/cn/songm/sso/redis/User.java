package cn.songm.sso.redis;

import cn.songm.common.beans.EntityAdapter;
import cn.songm.common.utils.StringUtils;

public class User extends EntityAdapter {

    private static final long serialVersionUID = -7388042848753495936L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
