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

import cn.songm.sso.entity.Session;

/**
 * 单点登入对外服务接口
 * 
 * @author zhangsong
 *
 */
public interface SongmSSOService {

    /**
     * 客户端报到请求
     * @param sessionId
     * @return
     */
    public Session report(String sessionId);
     
    /**
     * 用户登入登记
     * @param sessionId
     * @param userId
     * @param userInfo
     * @return
     */
    public Session login(String sessionId, String userId, String userInfo);

    /**
     * 用户退出登记
     * @param sessionId
     */
    public void logout(String sessionId);
    
    /**
     * 获取用户信息
     * @param sessionId
     * @return
     */
    public String getUserInfo(String sessionId);
    
    /**
     * 获取用户ID
     * @param sessionId
     * @return
     */
    public String getUserId(String sessionId);
    
    /**
     * 编辑用户信息
     * @param sessionId
     * @param userInfo
     */
    public void editUserInfo(String sessionId, String userInfo);
    
    /**
     * 设置Session中保存的属性
     * @param sessionId
     * @param key
     * @param value
     * @param expires
     */
    public void setSessionAttr(String sessionId, String key, String value, long expires);
    
    /**
     * 获取Session中保存的属性
     * @param sessionId
     * @param key
     * @return
     */
    public String getSessionAttr(String sessionId, String key);
    
    /**
     * 设置一个普通的验证码
     * @param sessionId
     * @param vcode
     * @return
     */
    public void setValidateCode(String sessionId, String vcode);
    
    /**
     * 获取一个普通的验证码
     * @param sessionId
     * @return
     */
    public String getValidateCode(String sessionId);
    
}
