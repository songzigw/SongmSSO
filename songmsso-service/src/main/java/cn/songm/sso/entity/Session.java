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

import java.io.Serializable;
import java.util.Date;

/**
 * 用户与服务端的会话
 *
 * @author zhangsong
 * @since 0.1, 2016-7-29
 * @version 0.1
 * 
 */
public class Session implements Serializable {

    private static final long serialVersionUID = 1689305158269907021L;

    /** 默认超时间 */
    public static final long TIME_OUT = 1000 * 60 * 60 * 24;

    public static final String USER_SESSION_KEY = "songmsso_sessionid";

    /** 会话唯一标示 */
    private String sesId;

    /** 用户ID */
    private String userId;

    /** 会话创建时间 */
    private Date created;

    /** 会话访问时间 */
    private Date access;

    public Session(String sesId) {
        this.sesId = sesId;
        created = new Date();
        access = created;
    }

    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setAccess(Date access) {
        this.access = access;
    }

    public Date getCreated() {
        return created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAccess() {
        return access;
    }

    public boolean isTimeout() {
        if (System.currentTimeMillis() - access.getTime() > TIME_OUT) {
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
        return "Session [sesId=" + sesId + ", userId=" + userId + ", created="
                + created + ", access=" + access + "]";
    }
}
