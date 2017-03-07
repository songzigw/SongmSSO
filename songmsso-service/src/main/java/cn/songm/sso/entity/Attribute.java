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

/**
 * 键值对属性描述
 * 
 * @author zhangsong
 *
 */
public class Attribute implements Serializable {

    private static final long serialVersionUID = -7512600852429959890L;

    /** 一般的验证码标识 */
    public static final String KEY_VCODE = "V_CODE";
    /** 用户登入信息标识 */
    public static final String KEY_LOGIN = "L_USER";

    /** SessionId */
    private String sesId;
    /** Key */
    private String key;
    /** Value */
    private String value;

    public Attribute(String sesId, String key, String value) {
        this.sesId = sesId;
        this.key = key;
        this.value = value;
    }

    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((sesId == null) ? 0 : sesId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Attribute other = (Attribute) obj;
        if (key == null) {
            if (other.key != null) return false;
        } else if (!key.equals(other.key)) return false;
        if (sesId == null) {
            if (other.sesId != null) return false;
        } else if (!sesId.equals(other.sesId)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Attribute [sesId=" + sesId + ", key=" + key + ", value=" + value
                + "]";
    }
}
