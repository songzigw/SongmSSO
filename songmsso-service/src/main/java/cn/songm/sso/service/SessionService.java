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
package cn.songm.sso.service;

import cn.songm.sso.entity.Attribute;
import cn.songm.sso.entity.Session;

/**
 * 用户会话业务逻辑
 * 
 * @author zhangsong
 * @since 0.1, 2016-8-23
 * @version 0.1
 *
 */
public interface SessionService {

    /**
     * 获取会话
     * @param sesId
     * @return
     */
    public Session getSession(String sesId);

    /**
     * 创建会话
     * @param sesId
     * @return
     */
    public Session createSession(String sesId);

    /**
     * 删除会话
     * @param sesId
     */
    public void removeSession(String sesId);

    /**
     * 设置会话属性
     * @param sesId
     * @param key
     * @param value
     */
    public void setAttr(String sesId, String key, String value);

    /**
     * 获取会话属性
     * @param sesId
     * @param key
     * @return
     */
    public Attribute getAttr(String sesId, String key);
    
    /**
     * 用户登入
     * @param sesId
     * @param userId
     * @param userInfo
     * @return
     */
    public Session regist(String sesId, String userId, String userInfo);

    /**
     * 编辑用户
     * @param userId
     * @param userInfo
     */
    public void editUser(String userId, String userInfo);

}
