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
package songm.sso.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import songm.sso.entity.Session;
import songm.sso.entity.User;
import songm.sso.service.SessionService;
import songm.sso.utils.Sequence;

@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    private Map<String, Session> sesItems = new HashMap<String, Session>();
    private Map<String, Set<Session>> userItems = new HashMap<String, Set<Session>>();

    // @Autowired
    // private SessionListenerManager sessionListenerManager;

    @Override
    public Session createSession(String sessionId) {
        Session ses = getSession(sessionId);
        if (ses != null) {
            return ses;
        }

        sessionId = Sequence.getInstance().getSequence(28);
        ses = new Session(sessionId);
        sesItems.put(sessionId, ses);

        return ses;
    }

    @Override
    public Session login(String sessionId, String userId, String userInfo) {
        Session ses = getSession(sessionId);
        if (ses == null) {
            sessionId = Sequence.getInstance().getSequence(28);
            ses = new Session(sessionId);
            sesItems.put(sessionId, ses);
        }

        ses.setUserId(userId);
        ses.setAttribute(User.INFO, userInfo);
        addUserSession(userId, ses);

        return ses;
    }

    public void addUserSession(String userId, Session ses) {
        Set<Session> setSes = userItems.get(userId);
        if (setSes == null) {
            setSes = new HashSet<Session>();
            userItems.put(userId, setSes);
        }
        setSes.remove(ses);
        setSes.add(ses);
    }

    public void removeUserSession(String userId, Session ses) {
        Set<Session> setSes = userItems.get(userId);
        if (setSes != null) {
            setSes.remove(ses);
            if (setSes.isEmpty()) {
                userItems.remove(userId);
            }
        }
    }

    @Override
    public Session getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        Session s = sesItems.get(sessionId);
        if (s != null) {
            s.updateAccess();
        }
        return s;
    }

    @Override
    public void removeSession(String sessionId) {
        Session ses = sesItems.remove(sessionId);
        if (ses != null) {
            removeUserSession(ses.getUserId(), ses);
        }
    }

    @Override
    public void setAttribute(String sessionId, String name, Object obj) {
        Session ses = getSession(sessionId);
        if (ses == null) {
            return;
        }
        ses.setAttribute(name, obj);
    }

    @Override
    public Object getAttribute(String sessionId, String name) {
        Session ses = getSession(sessionId);
        if (ses == null) {
            return null;
        }
        return ses.getAttribute(name);
    }

    @Override
    public void editUser(String userId, String userInfo) {
        Set<Session> setSes = userItems.get(userId);
        if (setSes != null) {
            for (Session ses : setSes) {
                ses.setAttribute(User.INFO, userInfo);
            }
        }
    }

}
