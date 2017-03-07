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
package cn.songm.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.songm.common.utils.Sequence;
import cn.songm.sso.entity.Attribute;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.AttributeRedis;
import cn.songm.sso.redis.SessionRedis;
import cn.songm.sso.service.SessionService;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    //private static final Logger LOG = LoggerFactory
    //        .getLogger(SessionServiceImpl.class);

    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private AttributeRedis attributeRedis;

    @Override
    public Session createSession(String sesId) {
        Session ses = getSession(sesId);
        if (ses != null) {
            return ses;
        }

        sesId = Sequence.getInstance().getSequence(28);
        return sessionRedis.insert(new Session(sesId));
    }

    @Override
    public Session regist(String sesId, String userId, String userInfo) {
        Session ses = getSession(sesId);
        if (ses == null) {
            ses = createSession(sesId);
        }
        ses.setUserId(userId);

        sessionRedis.updateUserId(sesId, userId);
        attributeRedis.insert(new Attribute(sesId, Attribute.KEY_LOGIN, userInfo));

        return ses;
    }

    @Override
    public Session getSession(String sesId) {
        if (sesId == null) return null;

        Session s = sessionRedis.queryById(sesId);
        if (s == null) return null;

        if (s.isTimeout()) {
            return null;
        }

        sessionRedis.updateAccess(sesId);
        attributeRedis.updateAccess(sesId);
        return s;
    }

    @Override
    public void removeSession(String sesId) {
        sessionRedis.delById(sesId);
        attributeRedis.delAttrsBySesId(sesId);
    }

    @Override
    public void setAttr(String sesId, String key, String value) {
        Session ses = getSession(sesId);
        if (ses == null) {
            return;
        }
        Attribute attr = new Attribute(sesId, key, value);
        attributeRedis.insert(attr);
    }

    @Override
    public Attribute getAttr(String sesId, String key) {
        Session ses = getSession(sesId);
        if (ses == null) {
            return null;
        }
        return attributeRedis.queryAttrById(sesId, key);
    }

    @Override
    public void editUser(String userId, String userInfo) {
        
    }

}
