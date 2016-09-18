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
package songm.sso.service;

import songm.sso.entity.Session;

/**
 * 用户会话管理
 * 
 * @author zhangsong
 * @since 0.1, 2016-8-23
 * @version 0.1
 *
 */
public interface SessionService {

    /**
     * 获取会话
     * @param sessionId
     * @return
     */
    public Session getSession(String sessionId);

    /**
     * 创建会话
     * @param sessionId
     * @return
     */
    public Session createSession(String sessionId);

    /**
     * 删除会话
     * @param sessionId
     */
    public void removeSession(String sessionId);

    /**
     * 设置会话属性
     * @param sessionId
     * @param name
     * @param obj
     */
    public void setAttribute(String sessionId, String name, Object obj);

    /**
     * 获取会话属性
     * @param sessionId
     * @param name
     * @return
     */
    public Object getAttribute(String sessionId, String name);
    
    /**
     * 用户登入
     * @param sessionId
     * @param userId
     * @param userInfo
     * @return
     */
    public Session login(String sessionId, String userId, String userInfo);

    /**
     * 编辑用户
     * @param userId
     * @param userInfo
     */
    public void editUser(String userId, String userInfo);

}
