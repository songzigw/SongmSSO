/*
 * Copyright [2016] [zhangsong <songm.cn>].
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package cn.songm.sso.entity;

import cn.songm.common.beans.Entity;

/**
 * 用户与服务端的会话
 *
 * @author zhangsong
 * @since 0.1, 2016-7-29
 * @version 0.1
 * 
 */
public class Session extends Entity {

    private static final long serialVersionUID = 1689305158269907021L;

    /** 默认超时间 */
    public static final long TIME_OUT = 1000 * 60 * 60 * 24 * 60;

    public static final String COOKIE_SESSIONID_KEY = "songmsso_sessionid";
    public static final String HEADER_SESSIONID_KEY = "Songmsso-Sessionid";

    /** 会话唯一标示 */
    private String sesId;

    /** 会话访问时间 */
    private Long access;
    
    /** 用户id */
    private String userId;
    
    /** 用户信息（JSON） */
    private String userInfo;
    
    /** 属性 [{key:XXX, value:XX, created: 123456789, expires: 1000}] */
    private String attribute;

    public Session() {
    }
    
    public Session(String sesId) {
        this.init();
        access = this.getCreated().getTime();
        this.sesId = sesId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

    public void setAccess(Long access) {
        this.access = access;
    }

    public Long getAccess() {
        return access;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean isTimeout() {
        if (System.currentTimeMillis() - access > TIME_OUT) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sesId == null) ? 0 : sesId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Session other = (Session) obj;
        if (sesId == null) {
            if (other.sesId != null) return false;
        } else if (!sesId.equals(other.sesId)) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sBui = new StringBuilder();
        sBui.append("Session [").append(super.toString());
        sBui.append(", sesId=").append(sesId);
        sBui.append(", access=").append(access);
        sBui.append(", userId=").append(userId);
        sBui.append(", userInfo=").append(userInfo);
        sBui.append(", attribute=").append(attribute)
        .append("]");
        
        return sBui.toString();
    }
    
}
