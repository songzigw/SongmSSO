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
package songm.sso.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import songm.sso.entity.Session;
import songm.sso.event.SessionEvent.EventType;

/**
 * Session监听器管理
 * <p>
 * 包括了，监听器的注册、移除，事件的触发等等。
 *
 * @author  zhangsong
 * @since   0.1, 2016-7-29
 * @version 0.1
 * 
 */
@Component("sessionListenerManager")
public class SessionListenerManager {

    private final Collection<SessionListener> listeners;

    public SessionListenerManager() {
        listeners = new HashSet<SessionListener>();
    }

    /**
     * 添加事件监听
     * 
     * @param listener
     */
    public void addListener(SessionListener listener) {
        listeners.add(listener);
    }

    /**
     * 移除事件监听
     * 
     * @param listener
     */
    public void removeListener(SessionListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(SessionEvent event) {
        Iterator<SessionListener> iter = listeners.iterator();
        EventType type = event.getSource();
        while (iter.hasNext()) {
            //SessionListener listener = (SessionListener) iter.next();
            if (EventType.CREATE.equals(type)) {
                //listener.onCreate(event);
            } else if (EventType.UPDATE.equals(type)) {
                //listener.onUpdate(event);
            } else if (EventType.UPDATE.equals(type)) {
                //listener.onRemove(event);
            }
        }
    }

    public void triggerCreate(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.CREATE, session);
        this.notifyListeners(event);
    }

    public void triggerUpdate(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.UPDATE, session);
        this.notifyListeners(event);
    }

    public void triggerRemove(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.REMOVE, session);
        this.notifyListeners(event);
    }
}
